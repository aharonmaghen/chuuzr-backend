package com.chuuzr.chuuzrbackend.dto.optiontype;

import com.chuuzr.chuuzrbackend.model.OptionType;

/**
 * Mapper utility for converting between OptionType entity and DTOs.
 */
public class OptionTypeMapper {

  /**
   * Converts an OptionType entity to an OptionTypeResponseDTO.
   */
  public static OptionTypeResponseDTO toResponseDTO(OptionType optionType) {
    if (optionType == null) {
      return null;
    }
    return new OptionTypeResponseDTO(
        optionType.getUuid(),
        optionType.getName(),
        optionType.getDescription(),
        optionType.getUpdatedAt(),
        optionType.getCreatedAt());
  }

  /**
   * Converts an OptionTypeRequestDTO to an OptionType entity.
   * Note: ID, UUID, and timestamps should be set separately.
   */
  public static OptionType toEntity(OptionTypeRequestDTO dto) {
    if (dto == null) {
      return null;
    }
    OptionType optionType = new OptionType();
    optionType.setName(dto.getName());
    optionType.setDescription(dto.getDescription());
    return optionType;
  }

  /**
   * Updates an existing OptionType entity with data from OptionTypeRequestDTO.
   * Preserves ID, UUID, and createdAt timestamp.
   */
  public static void updateEntityFromDTO(OptionType optionType, OptionTypeRequestDTO dto) {
    if (optionType == null || dto == null) {
      return;
    }
    optionType.setName(dto.getName());
    optionType.setDescription(dto.getDescription());
  }
}
