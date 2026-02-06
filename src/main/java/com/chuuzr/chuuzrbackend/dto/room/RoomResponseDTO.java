package com.chuuzr.chuuzrbackend.dto.room;

import java.time.LocalDateTime;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;

/**
 * DTO for returning Room data to API clients.
 * Only exposes UUID (not internal ID) and includes timestamps.
 */
public class RoomResponseDTO {
  private UUID uuid;
  private String name;
  private OptionTypeResponseDTO optionType;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public RoomResponseDTO() {
  }

  public RoomResponseDTO(UUID uuid, String name, OptionTypeResponseDTO optionType, LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this.uuid = uuid;
    this.name = name;
    this.optionType = optionType;
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

  public OptionTypeResponseDTO getOptionType() {
    return optionType;
  }

  public void setOptionType(OptionTypeResponseDTO optionType) {
    this.optionType = optionType;
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
