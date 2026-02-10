package com.chuuzr.chuuzrbackend.service;

import java.util.List;
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
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.repository.OptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomOptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;

@Service
@Transactional
public class RoomOptionService {

  private static final Logger logger = LoggerFactory.getLogger(RoomOptionService.class);

  private final RoomOptionRepository roomOptionRepository;
  private final RoomRepository roomRepository;
  private final OptionRepository optionRepository;
  private final OptionService optionService;

  @Autowired
  public RoomOptionService(RoomOptionRepository roomOptionRepository, RoomRepository roomRepository,
      OptionRepository optionRepository, OptionService optionService) {
    this.roomOptionRepository = roomOptionRepository;
    this.roomRepository = roomRepository;
    this.optionRepository = optionRepository;
    this.optionService = optionService;
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
      // Create new option using OptionService
      logger.debug("Option does not exist, creating new option");
      UUID createdOptionUuid = optionService.createOption(optionRequestDTO).getUuid();
      option = optionRepository.findByUuid(createdOptionUuid)
          .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_NOT_FOUND, "Option creation failed"));
    }

    // Check if option is already in room
    Optional<RoomOption> existingRoomOption = roomOptionRepository.findByUuids(roomUuid, option.getUuid());
    if (existingRoomOption.isPresent()) {
      logger.info("Option is already in room for roomUuid={}, optionUuid={}", roomUuid, option.getUuid());
      return RoomOptionMapper.toResponseDTO(existingRoomOption.get());
    }

    // Add option to room
    RoomOption roomOptionToSave = RoomOptionMapper.toEntity(room, option);
    roomOptionToSave.setScore(0);
    RoomOption savedRoomOption = roomOptionRepository.save(roomOptionToSave);
    logger.info("Option added to room for roomUuid={}, optionUuid={}", roomUuid, option.getUuid());
    return RoomOptionMapper.toResponseDTO(savedRoomOption);
  }
}
