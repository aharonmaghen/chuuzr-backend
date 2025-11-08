package com.chuuzr.chuuzrbackend.dto.roomuser;

import java.time.LocalDateTime;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;

/**
 * DTO for returning RoomUser data to API clients.
 * Includes the relationship UUID and nested Room/User information.
 */
public class RoomUserResponseDTO {
  private UUID uuid;
  private RoomResponseDTO room;
  private UserResponseDTO user;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public RoomUserResponseDTO() {
  }

  public RoomUserResponseDTO(UUID uuid, RoomResponseDTO room, UserResponseDTO user, LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this.uuid = uuid;
    this.room = room;
    this.user = user;
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

  public UserResponseDTO getUser() {
    return user;
  }

  public void setUser(UserResponseDTO user) {
    this.user = user;
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
