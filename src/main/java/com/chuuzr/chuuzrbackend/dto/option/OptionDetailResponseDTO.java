package com.chuuzr.chuuzrbackend.dto.option;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed option information response")
public class OptionDetailResponseDTO {
  @Schema(description = "Unique identifier for the option", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
  private UUID uuid;

  @Schema(description = "The option type this option belongs to")
  private OptionTypeResponseDTO optionType;

  @Schema(description = "External API provider identifier", example = "tmdb")
  private String apiProvider;

  @Schema(description = "Identifier in the external API", example = "550")
  private String externalId;

  @Schema(description = "Name of the option", example = "Fight Club")
  private String name;

  @Schema(description = "Description of the option", example = "An insomniac office worker and a devil-may-care soap maker form an underground fight club.")
  private String description;

  @Schema(description = "URL to the option image", example = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg", format = "uri")
  private String imageUrl;

  @Schema(description = "Additional metadata from the external provider")
  private Map<String, Object> metadata;

  @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime updatedAt;

  @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime createdAt;

  public OptionDetailResponseDTO() {
  }

  public OptionDetailResponseDTO(UUID uuid, OptionTypeResponseDTO optionType, String apiProvider, String externalId,
      String name, String description, String imageUrl, Map<String, Object> metadata,
      LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.uuid = uuid;
    this.optionType = optionType;
    this.apiProvider = apiProvider;
    this.externalId = externalId;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.metadata = metadata;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public OptionTypeResponseDTO getOptionType() {
    return optionType;
  }

  public void setOptionType(OptionTypeResponseDTO optionType) {
    this.optionType = optionType;
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

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
