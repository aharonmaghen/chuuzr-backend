package com.chuuzr.chuuzrbackend.dto.roomuser;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Room-user membership response")
public class RoomUserResponseDTO {
  @Schema(description = "The room in this membership")
  private RoomResponseDTO room;

  @Schema(description = "The user in this membership")
  private UserResponseDTO user;

  @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime updatedAt;

  @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime createdAt;

  public RoomUserResponseDTO() {
  }

  public RoomUserResponseDTO(RoomResponseDTO room, UserResponseDTO user, LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this.room = room;
    this.user = user;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
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
