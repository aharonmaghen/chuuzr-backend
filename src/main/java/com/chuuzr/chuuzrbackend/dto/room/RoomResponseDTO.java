package com.chuuzr.chuuzrbackend.dto.room;

import java.time.LocalDateTime;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Room information response")
public class RoomResponseDTO {
  @Schema(description = "Unique identifier for the room", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
  private UUID uuid;

  @Schema(description = "Name of the room", example = "Friday Movie Night")
  private String name;

  @Schema(description = "The option type associated with this room")
  private OptionTypeResponseDTO optionType;

  @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime updatedAt;

  @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00", format = "date-time")
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
