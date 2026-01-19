package com.chuuzr.chuuzrbackend.dto.roomuser;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;

public class RoomUserResponseDTO {
  private RoomResponseDTO room;
  private UserResponseDTO user;
  private LocalDateTime updatedAt;
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
