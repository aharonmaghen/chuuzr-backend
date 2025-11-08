package com.chuuzr.chuuzrbackend.dto.room;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for returning Room data to API clients.
 * Only exposes UUID (not internal ID) and includes timestamps.
 */
public class RoomResponseDTO {
  private UUID uuid;
  private String name;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public RoomResponseDTO() {
  }

  public RoomResponseDTO(UUID uuid, String name, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.uuid = uuid;
    this.name = name;
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
