package com.chuuzr.chuuzrbackend.dto.roomoption;

import java.time.LocalDateTime;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.option.OptionResponseDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;

/**
 * DTO for returning RoomOption data to API clients.
 * Includes the relationship UUID and nested Room/Option information.
 */
public class RoomOptionResponseDTO {
  private UUID uuid;
  private RoomResponseDTO room;
  private OptionResponseDTO option;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public RoomOptionResponseDTO() {
  }

  public RoomOptionResponseDTO(UUID uuid, RoomResponseDTO room, OptionResponseDTO option,
      LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.uuid = uuid;
    this.room = room;
    this.option = option;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public RoomResponseDTO getRoom() {
    return room;
  }

  public void setRoom(RoomResponseDTO room) {
    this.room = room;
  }

  public OptionResponseDTO getOption() {
    return option;
  }

  public void setOption(OptionResponseDTO option) {
    this.option = option;
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
