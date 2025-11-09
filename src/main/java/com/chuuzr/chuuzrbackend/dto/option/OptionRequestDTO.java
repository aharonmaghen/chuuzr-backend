package com.chuuzr.chuuzrbackend.dto.option;

import java.util.UUID;

/**
 * DTO for creating or updating an Option.
 * Does not include UUID or timestamps as these are managed by the server.
 */
public class OptionRequestDTO {
  private UUID optionTypeUuid;
  private String apiProvider;
  private String externalId;
  private String name;
  private String description;
  private String imageUrl;

  public OptionRequestDTO() {
  }

  public OptionRequestDTO(UUID optionTypeUuid, String apiProvider, String externalId,
      String name, String description, String imageUrl) {
    this.optionTypeUuid = optionTypeUuid;
    this.apiProvider = apiProvider;
    this.externalId = externalId;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
  }

  public UUID getOptionTypeUuid() {
    return optionTypeUuid;
  }

  public void setOptionTypeUuid(UUID optionTypeUuid) {
    this.optionTypeUuid = optionTypeUuid;
  }

  public String getApiProvider() {
    return apiProvider;
  }

  public void setApiProvider(String apiProvider) {
    this.apiProvider = apiProvider;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
