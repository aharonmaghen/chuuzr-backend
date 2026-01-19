package com.chuuzr.chuuzrbackend.dto.roomoption;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.dto.option.OptionResponseDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;

public class RoomOptionResponseDTO {
  private RoomResponseDTO room;
  private OptionResponseDTO option;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public RoomOptionResponseDTO() {
  }

  public RoomOptionResponseDTO(RoomResponseDTO room, OptionResponseDTO option,
      LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.room = room;
    this.option = option;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
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
