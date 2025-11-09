package com.chuuzr.chuuzrbackend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeMapper;
import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeRequestDTO;
import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;
import com.chuuzr.chuuzrbackend.model.OptionType;
import com.chuuzr.chuuzrbackend.repository.OptionTypeRepository;

/**
 * Service layer for OptionType business logic.
 * Handles transactions and coordinates between controller and repository.
 */
@Service
@Transactional
public class OptionTypeService {

  private final OptionTypeRepository optionTypeRepository;

  @Autowired
  public OptionTypeService(OptionTypeRepository optionTypeRepository) {
    this.optionTypeRepository = optionTypeRepository;
  }

  /**
   * Finds an option type by UUID and returns the DTO.
   *
   * @param optionTypeUuid The UUID of the option type to find
   * @return OptionTypeResponseDTO if found, null otherwise
   */
  @Transactional(readOnly = true)
  public OptionTypeResponseDTO findByUuid(UUID optionTypeUuid) {
    OptionType optionType = optionTypeRepository.findByUuid(optionTypeUuid).orElse(null);
    return OptionTypeMapper.toResponseDTO(optionType);
  }

  /**
   * Gets all option types (paginated).
   *
   * @param pageable Pagination parameters
   * @return List of option types
   */
  @Transactional(readOnly = true)
  public List<OptionTypeResponseDTO> getAllOptionTypes(Pageable pageable) {
    Page<OptionType> page = optionTypeRepository.findAll(pageable);
    return page.getContent().stream()
        .map(OptionTypeMapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  /**
   * Creates a new option type.
   *
   * @param optionTypeRequestDTO The option type data to create
   * @return The created option type as a response DTO
   */
  public OptionTypeResponseDTO createOptionType(OptionTypeRequestDTO optionTypeRequestDTO) {
    OptionType optionType = new OptionType();
    optionType.setName(optionTypeRequestDTO.getName());
    optionType.setDescription(optionTypeRequestDTO.getDescription());
    OptionType savedOptionType = optionTypeRepository.save(optionType);
    return OptionTypeMapper.toResponseDTO(savedOptionType);
  }

  /**
   * Updates an existing option type.
   *
   * @param optionTypeUuid       The UUID of the option type to update
   * @param optionTypeRequestDTO The updated option type data
   * @return The updated option type as a response DTO, or null if option type not
   *         found
   */
  public OptionTypeResponseDTO updateOptionType(UUID optionTypeUuid, OptionTypeRequestDTO optionTypeRequestDTO) {
    OptionType optionType = optionTypeRepository.findByUuid(optionTypeUuid).orElse(null);
    if (optionType == null) {
      return null;
    }
    OptionTypeMapper.updateEntityFromDTO(optionType, optionTypeRequestDTO);
    OptionType updatedOptionType = optionTypeRepository.save(optionType);
    return OptionTypeMapper.toResponseDTO(updatedOptionType);
  }
}
