package com.chuuzr.chuuzrbackend.dto.roomoption;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.dto.option.OptionSummaryResponseDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Room-option association response")
public class RoomOptionResponseDTO {
  @Schema(description = "The room in this association")
  private RoomResponseDTO room;

  @Schema(description = "The option in this association")
  private OptionSummaryResponseDTO option;

  @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime updatedAt;

  @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00", format = "date-time")
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
