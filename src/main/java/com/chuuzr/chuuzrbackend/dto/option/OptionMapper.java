package com.chuuzr.chuuzrbackend.dto.option;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeMapper;
import com.chuuzr.chuuzrbackend.model.Option;

public class OptionMapper {

  public static OptionSummaryResponseDTO toSummaryDTO(Option option) {
    if (option == null) {
      return null;
    }
    return new OptionSummaryResponseDTO(
        option.getUuid(),
        OptionTypeMapper.toResponseDTO(option.getOptionType()),
        option.getApiProvider(),
        option.getExternalId(),
        option.getName(),
        option.getDescription(),
        option.getImageUrl());
  }

  public static OptionDetailResponseDTO toDetailDTO(Option option) {
    if (option == null) {
      return null;
    }
    return new OptionDetailResponseDTO(
        option.getUuid(),
        OptionTypeMapper.toResponseDTO(option.getOptionType()),
        option.getApiProvider(),
        option.getExternalId(),
        option.getName(),
        option.getDescription(),
        option.getImageUrl(),
        option.getMetadata(),
        option.getUpdatedAt(),
        option.getCreatedAt());
  }

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
