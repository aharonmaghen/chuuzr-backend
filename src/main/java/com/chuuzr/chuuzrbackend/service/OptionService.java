package com.chuuzr.chuuzrbackend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.option.OptionMapper;
import com.chuuzr.chuuzrbackend.dto.option.OptionRequestDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.exception.ValidationException;
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.OptionType;
import com.chuuzr.chuuzrbackend.repository.OptionRepository;
import com.chuuzr.chuuzrbackend.repository.OptionTypeRepository;

@Service
@Transactional
public class OptionService {

  private final OptionRepository optionRepository;
  private final OptionTypeRepository optionTypeRepository;

  @Autowired
  public OptionService(OptionRepository optionRepository, OptionTypeRepository optionTypeRepository) {
    this.optionRepository = optionRepository;
    this.optionTypeRepository = optionTypeRepository;
  }

  @Transactional(readOnly = true)
  public OptionResponseDTO findByUuid(UUID optionUuid) {
    Option option = optionRepository.findByUuid(optionUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_NOT_FOUND,
            "Option with UUID " + optionUuid + " not found"));
    return OptionMapper.toResponseDTO(option);
  }

  @Transactional(readOnly = true)
  public List<OptionResponseDTO> findByOptionTypeUuid(UUID optionTypeUuid, Pageable pageable) {
    Page<Option> page = optionRepository.findByOptionTypeUuid(optionTypeUuid, pageable);
    return page.getContent().stream()
        .map(OptionMapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  public OptionResponseDTO createOption(OptionRequestDTO optionRequestDTO) {
    if (optionRequestDTO.getOptionTypeUuid() == null) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Option type UUID is required");
    }
    OptionType optionType = optionTypeRepository.findByUuid(optionRequestDTO.getOptionTypeUuid())
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_TYPE_NOT_FOUND,
            "Option type with UUID " + optionRequestDTO.getOptionTypeUuid() + " not found"));
    Option optionToSave = OptionMapper.toEntity(optionRequestDTO);
    optionToSave.setOptionType(optionType);

    Option savedOption = optionRepository.save(optionToSave);
    return OptionMapper.toResponseDTO(savedOption);
  }

  public OptionResponseDTO updateOption(UUID optionUuid, OptionRequestDTO optionRequestDTO) {
    Option option = optionRepository.findByUuid(optionUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_NOT_FOUND,
            "Option with UUID " + optionUuid + " not found"));

    if (optionRequestDTO.getOptionTypeUuid() != null) {
      OptionType optionType = optionTypeRepository.findByUuid(optionRequestDTO.getOptionTypeUuid())
          .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.OPTION_TYPE_NOT_FOUND,
              "Option type with UUID " + optionRequestDTO.getOptionTypeUuid() + " not found"));
      option.setOptionType(optionType);
    }

    OptionMapper.updateEntityFromDTO(option, optionRequestDTO);
    Option updatedOption = optionRepository.save(option);
    return OptionMapper.toResponseDTO(updatedOption);
  }
}
