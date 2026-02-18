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

import com.chuuzr.chuuzrbackend.dto.option.OptionDetailResponseDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionMapper;
import com.chuuzr.chuuzrbackend.dto.option.OptionRequestDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionSummaryResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.exception.ValidationException;
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.OptionType;
import com.chuuzr.chuuzrbackend.repository.OptionRepository;
import com.chuuzr.chuuzrbackend.repository.OptionTypeRepository;
import com.chuuzr.chuuzrbackend.repository.SearchProviderRepository;
import com.chuuzr.chuuzrbackend.service.search.SearchProviderService;
import com.chuuzr.chuuzrbackend.service.search.SearchProviderFactory;

@Service
@Transactional
public class OptionService {

  private static final Logger logger = LoggerFactory.getLogger(OptionService.class);

  private final OptionRepository optionRepository;
  private final OptionTypeRepository optionTypeRepository;
  private final SearchProviderFactory searchProviderFactory;
  private final SearchProviderRepository searchProviderRepository;

  @Autowired
  public OptionService(OptionRepository optionRepository, OptionTypeRepository optionTypeRepository,
      SearchProviderFactory searchProviderFactory,
      SearchProviderRepository searchProviderRepository) {
    this.optionRepository = optionRepository;
    this.optionTypeRepository = optionTypeRepository;
    this.searchProviderFactory = searchProviderFactory;
    this.searchProviderRepository = searchProviderRepository;
  }

  @Transactional(readOnly = true)
  public OptionDetailResponseDTO findByUuid(UUID optionUuid) {
    logger.debug("Finding option by uuid={}", optionUuid);
    Option option = optionRepository.findByUuid(optionUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_NOT_FOUND,
            "Option with UUID " + optionUuid + " not found"));

    return OptionMapper.toDetailDTO(option);
  }

  @Transactional(readOnly = true)
  public List<OptionSummaryResponseDTO> findByOptionTypeUuid(UUID optionTypeUuid, Pageable pageable) {
    logger.debug("Finding options by optionTypeUuid={}", optionTypeUuid);
    Page<Option> page = optionRepository.findByOptionTypeUuid(optionTypeUuid, pageable);
    return page.getContent().stream()
        .map(OptionMapper::toSummaryDTO)
        .collect(Collectors.toList());
  }

  public Option findOrCreateOption(String apiProvider, String externalId, OptionType optionType) {
    logger.debug("Finding or creating option for apiProvider={}, externalId={}", apiProvider, externalId);
    Optional<Option> existing = optionRepository
        .findByApiProviderAndExternalIdAndOptionTypeUuid(apiProvider, externalId, optionType.getUuid());
    if (existing.isPresent()) {
      return existing.get();
    }

    validateProviderMapping(optionType, apiProvider);

    Option option = new Option();
    option.setApiProvider(apiProvider);
    option.setExternalId(externalId);
    option.setOptionType(optionType);

    SearchProviderService provider = searchProviderFactory.getProviderByKey(apiProvider);
    Map<String, Object> metadata = provider.fetchOptionMetadata(externalId);
    option.setMetadata(metadata);
    provider.applyMetadataToOption(option, metadata);

    validateOptionFields(option);

    Option savedOption = optionRepository.save(option);
    logger.info("Option created with uuid={}", savedOption.getUuid());
    return savedOption;
  }

  public OptionDetailResponseDTO createOption(OptionRequestDTO optionRequestDTO) {
    logger.debug("Creating option");
    if (optionRequestDTO.getOptionTypeUuid() == null) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Option type UUID is required");
    }
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

    logger.debug("Option saved with uuid={}", savedOption.getUuid());

    return OptionMapper.toDetailDTO(savedOption);
  }

  public OptionDetailResponseDTO updateOption(UUID optionUuid, OptionRequestDTO optionRequestDTO) {
    logger.debug("Updating option with uuid={}", optionUuid);
    Option option = optionRepository.findByUuid(optionUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_NOT_FOUND,
            "Option with UUID " + optionUuid + " not found"));

    if (optionRequestDTO.getOptionTypeUuid() == null) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Option type UUID is required");
    }
    OptionType optionType = optionTypeRepository.findByUuid(optionRequestDTO.getOptionTypeUuid())
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_TYPE_NOT_FOUND,
            "Option type with UUID " + optionRequestDTO.getOptionTypeUuid() + " not found"));
    option.setOptionType(optionType);

    validateProviderMapping(optionType, optionRequestDTO.getApiProvider());

    OptionMapper.updateEntityFromDTO(option, optionRequestDTO);

    SearchProviderService provider = searchProviderFactory.getProviderByKey(optionRequestDTO.getApiProvider());
    Map<String, Object> metadata = provider.fetchOptionMetadata(optionRequestDTO.getExternalId());
    option.setMetadata(metadata);
    provider.applyMetadataToOption(option, metadata);

    validateOptionFields(option);

    Option updatedOption = optionRepository.save(option);
    logger.info("Option updated with uuid={}", optionUuid);

    return OptionMapper.toDetailDTO(updatedOption);
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
