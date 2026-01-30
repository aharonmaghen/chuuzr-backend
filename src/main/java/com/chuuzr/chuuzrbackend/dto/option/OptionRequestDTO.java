package com.chuuzr.chuuzrbackend.dto.option;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidImageUrl;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidName;

public class OptionRequestDTO {
  @NotNull(message = "Option type UUID is required")
  private UUID optionTypeUuid;

  @NotBlank(message = "API provider is required")
  @Size(min = 1, max = 50, message = "API provider must be between 1 and 50 characters")
  @Pattern(regexp = "^[a-zA-Z0-9_-]{1,50}$", message = "API provider can only contain letters, numbers, underscores, and hyphens")
  private String apiProvider;

  @NotBlank(message = "External ID is required")
  @Size(min = 1, max = 100, message = "External ID must be between 1 and 100 characters")
  @Pattern(regexp = "^[a-zA-Z0-9_.-]{1,100}$", message = "External ID can only contain letters, numbers, underscores, hyphens, and dots")
  private String externalId;

  @NotBlank(message = "Name is required")
  @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
  @ValidName
  private String name;

  @NotBlank(message = "Description is required")
  @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
  private String description;

  @ValidImageUrl
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
