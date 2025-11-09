package com.chuuzr.chuuzrbackend.dto.optiontype;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for returning OptionType data to API clients.
 */
public class OptionTypeResponseDTO {
  private UUID uuid;
  private String name;
  private String description;
  private LocalDateTime updatedAt;
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
