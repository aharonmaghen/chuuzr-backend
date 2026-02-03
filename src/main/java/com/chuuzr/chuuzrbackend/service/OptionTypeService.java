package com.chuuzr.chuuzrbackend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeMapper;
import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeRequestDTO;
import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.model.OptionType;
import com.chuuzr.chuuzrbackend.repository.OptionTypeRepository;

@Service
@Transactional
public class OptionTypeService {

  private static final Logger logger = LoggerFactory.getLogger(OptionTypeService.class);

  private final OptionTypeRepository optionTypeRepository;

  @Autowired
  public OptionTypeService(OptionTypeRepository optionTypeRepository) {
    this.optionTypeRepository = optionTypeRepository;
  }

  @Transactional(readOnly = true)
  public OptionTypeResponseDTO findByUuid(UUID optionTypeUuid) {
    logger.debug("Finding option type by uuid={}", optionTypeUuid);
    OptionType optionType = optionTypeRepository.findByUuid(optionTypeUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_TYPE_NOT_FOUND,
            "Option type with UUID " + optionTypeUuid + " not found"));
    return OptionTypeMapper.toResponseDTO(optionType);
  }

  @Transactional(readOnly = true)
  public List<OptionTypeResponseDTO> getAllOptionTypes(Pageable pageable) {
    logger.debug("Fetching all option types");
    Page<OptionType> page = optionTypeRepository.findAll(pageable);
    return page.getContent().stream()
        .map(OptionTypeMapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  public OptionTypeResponseDTO createOptionType(OptionTypeRequestDTO optionTypeRequestDTO) {
    logger.debug("Creating option type");
    OptionType optionTypeToSave = new OptionType();
    optionTypeToSave.setName(optionTypeRequestDTO.getName());
    optionTypeToSave.setDescription(optionTypeRequestDTO.getDescription());
    OptionType savedOptionType = optionTypeRepository.save(optionTypeToSave);
    logger.debug("Option type saved with uuid={}", savedOptionType.getUuid());
    return OptionTypeMapper.toResponseDTO(savedOptionType);
  }

  public OptionTypeResponseDTO updateOptionType(UUID optionTypeUuid, OptionTypeRequestDTO optionTypeRequestDTO) {
    logger.debug("Updating option type with uuid={}", optionTypeUuid);
    OptionType optionType = optionTypeRepository.findByUuid(optionTypeUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_TYPE_NOT_FOUND,
            "Option type with UUID " + optionTypeUuid + " not found"));

    OptionTypeMapper.updateEntityFromDTO(optionType, optionTypeRequestDTO);
    OptionType updatedOptionType = optionTypeRepository.save(optionType);
    logger.info("Option type updated with uuid={}", optionTypeUuid);
    return OptionTypeMapper.toResponseDTO(updatedOptionType);
  }
}
