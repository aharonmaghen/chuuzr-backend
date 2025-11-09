package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.option.OptionMapper;
import com.chuuzr.chuuzrbackend.dto.option.OptionRequestDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionResponseDTO;
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.OptionType;
import com.chuuzr.chuuzrbackend.repository.OptionRepository;
import com.chuuzr.chuuzrbackend.repository.OptionTypeRepository;

/**
 * Service layer for Option business logic.
 * Handles transactions and coordinates between controller and repository.
 */
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

  /**
   * Finds an option by UUID and returns the DTO.
   *
   * @param optionUuid The UUID of the option to find
   * @return OptionResponseDTO if found, null otherwise
   */
  @Transactional(readOnly = true)
  public OptionResponseDTO findByUuid(UUID optionUuid) {
    Option option = optionRepository.findByUuid(optionUuid).orElse(null);
    return OptionMapper.toResponseDTO(option);
  }

  /**
   * Creates a new option.
   *
   * @param optionRequestDTO The option data to create
   * @return The created option as a response DTO, or null if option type not
   *         found
   */
  public OptionResponseDTO createOption(OptionRequestDTO optionRequestDTO) {
    if (optionRequestDTO.getOptionTypeUuid() == null) {
      return null;
    }
    OptionType optionType = optionTypeRepository.findByUuid(optionRequestDTO.getOptionTypeUuid()).orElse(null);
    if (optionType == null) {
      return null;
    }
    Option option = OptionMapper.toEntity(optionRequestDTO);
    option.setOptionType(optionType);

    Option savedOption = optionRepository.save(option);
    return OptionMapper.toResponseDTO(savedOption);
  }

  /**
   * Updates an existing option.
   *
   * @param optionUuid       The UUID of the option to update
   * @param optionRequestDTO The updated option data
   * @return The updated option as a response DTO, or null if option or option
   *         type not found
   */
  public OptionResponseDTO updateOption(UUID optionUuid, OptionRequestDTO optionRequestDTO) {
    Option option = optionRepository.findByUuid(optionUuid).orElse(null);
    if (option == null) {
      return null;
    }
    if (optionRequestDTO.getOptionTypeUuid() != null) {
      OptionType optionType = optionTypeRepository.findByUuid(optionRequestDTO.getOptionTypeUuid()).orElse(null);
      if (optionType == null) {
        return null;
      }
      option.setOptionType(optionType);
    }
    OptionMapper.updateEntityFromDTO(option, optionRequestDTO);

    Option updatedOption = optionRepository.save(option);
    return OptionMapper.toResponseDTO(updatedOption);
  }
}
