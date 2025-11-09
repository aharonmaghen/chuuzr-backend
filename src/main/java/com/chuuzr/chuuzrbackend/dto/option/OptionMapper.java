package com.chuuzr.chuuzrbackend.dto.option;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeMapper;
import com.chuuzr.chuuzrbackend.model.Option;

/**
 * Mapper utility for converting between Option entity and DTOs.
 */
public class OptionMapper {

  /**
   * Converts an Option entity to an OptionResponseDTO.
   */
  public static OptionResponseDTO toResponseDTO(Option option) {
    if (option == null) {
      return null;
    }
    return new OptionResponseDTO(
        option.getUuid(),
        OptionTypeMapper.toResponseDTO(option.getOptionType()),
        option.getApiProvider(),
        option.getExternalId(),
        option.getName(),
        option.getDescription(),
        option.getImageUrl(),
        option.getUpdatedAt(),
        option.getCreatedAt());
  }

  /**
   * Converts an OptionRequestDTO to an Option entity.
   * Note: ID, UUID, timestamps, and OptionType relationship should be set
   * separately.
   */
  public static Option toEntity(OptionRequestDTO dto) {
    if (dto == null) {
      return null;
    }
    Option option = new Option();
    option.setApiProvider(dto.getApiProvider());
    option.setExternalId(dto.getExternalId());
    option.setName(dto.getName());
    option.setDescription(dto.getDescription());
    option.setImageUrl(dto.getImageUrl());
    return option;
  }

  /**
   * Updates an existing Option entity with data from OptionRequestDTO.
   * Preserves ID, UUID, createdAt timestamp, and OptionType relationship.
   */
  public static void updateEntityFromDTO(Option option, OptionRequestDTO dto) {
    if (option == null || dto == null) {
      return;
    }
    option.setApiProvider(dto.getApiProvider());
    option.setExternalId(dto.getExternalId());
    option.setName(dto.getName());
    option.setDescription(dto.getDescription());
    option.setImageUrl(dto.getImageUrl());
  }
}
