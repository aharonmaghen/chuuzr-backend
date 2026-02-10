package com.chuuzr.chuuzrbackend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.option.OptionMapper;
import com.chuuzr.chuuzrbackend.dto.option.OptionRequestDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionSummaryResponseDTO;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionMapper;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.exception.ValidationException;
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.OptionType;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.repository.OptionRepository;
import com.chuuzr.chuuzrbackend.repository.OptionTypeRepository;
import com.chuuzr.chuuzrbackend.repository.RoomOptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;
import com.chuuzr.chuuzrbackend.repository.SearchProviderRepository;
import com.chuuzr.chuuzrbackend.service.search.SearchProviderService;
import com.chuuzr.chuuzrbackend.service.search.SearchProviderFactory;

@Service
@Transactional
public class RoomOptionService {

  private static final Logger logger = LoggerFactory.getLogger(RoomOptionService.class);

  private final RoomOptionRepository roomOptionRepository;
  private final RoomRepository roomRepository;
  private final OptionRepository optionRepository;
  private final OptionTypeRepository optionTypeRepository;
  private final SearchProviderFactory searchProviderFactory;
  private final SearchProviderRepository searchProviderRepository;

  @Autowired
  public RoomOptionService(RoomOptionRepository roomOptionRepository, RoomRepository roomRepository,
      OptionRepository optionRepository, OptionTypeRepository optionTypeRepository,
      SearchProviderFactory searchProviderFactory, SearchProviderRepository searchProviderRepository) {
    this.roomOptionRepository = roomOptionRepository;
    this.roomRepository = roomRepository;
    this.optionRepository = optionRepository;
    this.optionTypeRepository = optionTypeRepository;
    this.searchProviderFactory = searchProviderFactory;
    this.searchProviderRepository = searchProviderRepository;
  }

  @Transactional(readOnly = true)
  public List<OptionSummaryResponseDTO> getRoomOptions(UUID roomUuid, Pageable pageable) {
    logger.debug("Fetching room options for roomUuid={}", roomUuid);
    Page<RoomOption> page = roomOptionRepository.findByRoomUuid(roomUuid, pageable);
    return page.getContent().stream().map(RoomOption::getOption).map(OptionMapper::toSummaryDTO)
        .collect(Collectors.toList());
  }

  public RoomOptionResponseDTO addOptionToRoom(UUID roomUuid, UUID optionUuid) {
    logger.debug("Adding option to room roomUuid={}, optionUuid={}", roomUuid, optionUuid);
    Room room = roomRepository.findByUuid(roomUuid).orElseThrow(
        () -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND,
            "Room with UUID " + roomUuid + " not found"));

    Option option = optionRepository.findByUuid(optionUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_NOT_FOUND,
            "Option with UUID " + optionUuid + " not found"));

    RoomOption roomOptionToSave = RoomOptionMapper.toEntity(room, option);
    roomOptionToSave.setScore(0);
    RoomOption savedRoomOption = roomOptionRepository.save(roomOptionToSave);
    return RoomOptionMapper.toResponseDTO(savedRoomOption);
  }

  public RoomOptionResponseDTO addOptionToRoom(UUID roomUuid, OptionRequestDTO optionRequestDTO) {
    logger.debug("Adding option to room roomUuid={}", roomUuid);
    Room room = roomRepository.findByUuid(roomUuid).orElseThrow(
        () -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND,
            "Room with UUID " + roomUuid + " not found"));

    // Validate input
    if (optionRequestDTO.getOptionTypeUuid() == null) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Option type UUID is required");
    }

    // Check if option already exists
    Optional<Option> existingOption = optionRepository.findByApiProviderAndExternalIdAndOptionTypeUuid(
        optionRequestDTO.getApiProvider(), 
        optionRequestDTO.getExternalId(), 
        optionRequestDTO.getOptionTypeUuid());

    Option option;
    if (existingOption.isPresent()) {
      // Recycle existing option
      logger.debug("Option already exists, recycling option with uuid={}", existingOption.get().getUuid());
      option = existingOption.get();
    } else {
      // Create new option
      logger.debug("Option does not exist, creating new option");
      option = createOption(optionRequestDTO);
    }

    // Add option to room
    RoomOption roomOptionToSave = RoomOptionMapper.toEntity(room, option);
    roomOptionToSave.setScore(0);
    RoomOption savedRoomOption = roomOptionRepository.save(roomOptionToSave);
    logger.info("Option added to room for roomUuid={}, optionUuid={}", roomUuid, option.getUuid());
    return RoomOptionMapper.toResponseDTO(savedRoomOption);
  }

  private Option createOption(OptionRequestDTO optionRequestDTO) {
    logger.debug("Creating option in RoomOptionService");
    OptionType optionType = optionTypeRepository.findByUuid(optionRequestDTO.getOptionTypeUuid())
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_TYPE_NOT_FOUND,
            "Option type with UUID " + optionRequestDTO.getOptionTypeUuid() + " not found"));

    validateProviderMapping(optionType, optionRequestDTO.getApiProvider());

    Option optionToSave = OptionMapper.toEntity(optionRequestDTO);
    optionToSave.setOptionType(optionType);

    SearchProviderService provider = searchProviderFactory.getProviderByKey(optionRequestDTO.getApiProvider());
    Map<String, Object> metadata = provider.fetchOptionMetadata(optionRequestDTO.getExternalId());
    optionToSave.setMetadata(metadata);
    provider.applyMetadataToOption(optionToSave, metadata);

    validateOptionFields(optionToSave);

    Option savedOption = optionRepository.save(optionToSave);
    logger.debug("Option created with uuid={}", savedOption.getUuid());
    return savedOption;
  }

  private void validateOptionFields(Option option) {
    if (option == null) {
      return;
    }
    if (option.getName() == null || option.getName().trim().isEmpty()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Option name is required");
    }
    if (option.getDescription() == null || option.getDescription().trim().isEmpty()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Option description is required");
    }
  }

  private void validateProviderMapping(OptionType optionType, String apiProvider) {
    if (optionType == null || optionType.getId() == null) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Option type is required");
    }
    if (apiProvider == null || apiProvider.trim().isEmpty()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "API provider is required");
    }
    boolean exists = searchProviderRepository
        .existsByOptionTypeIdAndProviderKeyIgnoreCaseAndEnabledTrue(optionType.getId(), apiProvider.trim());
    if (!exists) {
      throw new ValidationException(ErrorCode.INVALID_INPUT,
          "API provider is not configured for the specified option type");
    }
  }
}
