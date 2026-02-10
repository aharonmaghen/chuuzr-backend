package com.chuuzr.chuuzrbackend.dto.roomoption;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.dto.option.OptionSummaryResponseDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;

public class RoomOptionResponseDTO {
  private RoomResponseDTO room;
  private OptionSummaryResponseDTO option;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public RoomOptionResponseDTO() {
  }

  public RoomOptionResponseDTO(RoomResponseDTO room, OptionSummaryResponseDTO option,
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

  public OptionSummaryResponseDTO getOption() {
    return option;
  }

  public void setOption(OptionSummaryResponseDTO option) {
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
