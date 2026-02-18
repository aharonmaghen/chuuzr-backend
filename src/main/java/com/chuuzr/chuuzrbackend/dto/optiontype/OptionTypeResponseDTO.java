package com.chuuzr.chuuzrbackend.dto.optiontype;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Option type information response")
public class OptionTypeResponseDTO {
  @Schema(description = "Unique identifier for the option type", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
  private UUID uuid;

  @Schema(description = "Name of the option type", example = "Movies")
  private String name;

  @Schema(description = "Description of the option type", example = "Feature films and documentaries")
  private String description;

  @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime updatedAt;

  @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime createdAt;

  public OptionTypeResponseDTO() {
  }

  public OptionTypeResponseDTO(UUID uuid, String name, String description,
      LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.uuid = uuid;
    this.name = name;
    this.description = description;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
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
