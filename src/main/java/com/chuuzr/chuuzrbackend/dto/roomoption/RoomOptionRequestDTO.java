package com.chuuzr.chuuzrbackend.dto.roomoption;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to add an option to a room")
public class RoomOptionRequestDTO {
  @Schema(description = "External API provider identifier", example = "tmdb")
  @NotBlank(message = "API provider is required")
  @Size(min = 1, max = 50, message = "API provider must be between 1 and 50 characters")
  @Pattern(regexp = "^[a-zA-Z0-9_-]{1,50}$", message = "API provider can only contain letters, numbers, underscores, and hyphens")
  private String apiProvider;

  @Schema(description = "Identifier in the external API", example = "550")
  @NotBlank(message = "External ID is required")
  @Size(min = 1, max = 100, message = "External ID must be between 1 and 100 characters")
  @Pattern(regexp = "^[a-zA-Z0-9_.-]{1,100}$", message = "External ID can only contain letters, numbers, underscores, hyphens, and dots")
  private String externalId;

  public RoomOptionRequestDTO() {
  }

  public RoomOptionRequestDTO(String apiProvider, String externalId) {
    this.apiProvider = apiProvider;
    this.externalId = externalId;
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
}
