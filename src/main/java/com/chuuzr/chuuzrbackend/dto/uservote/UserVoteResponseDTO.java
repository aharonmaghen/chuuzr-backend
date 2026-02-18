package com.chuuzr.chuuzrbackend.dto.uservote;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionResponseDTO;
import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserResponseDTO;
import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User vote response")
public class UserVoteResponseDTO {
  @Schema(description = "The room-user membership for this vote")
  private RoomUserResponseDTO roomUser;

  @Schema(description = "The room-option association for this vote")
  private RoomOptionResponseDTO roomOption;

  @Schema(description = "The vote direction", example = "UP")
  private VoteType voteType;

  @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime createdAt;

  @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime updatedAt;

  public UserVoteResponseDTO() {
  }

  public UserVoteResponseDTO(RoomUserResponseDTO roomUser, RoomOptionResponseDTO roomOption, VoteType voteType,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.roomUser = roomUser;
    this.roomOption = roomOption;
    this.voteType = voteType;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public RoomUserResponseDTO getRoomUser() {
    return roomUser;
  }

  public void setRoomUser(RoomUserResponseDTO roomUser) {
    this.roomUser = roomUser;
  }

  public RoomOptionResponseDTO getRoomOption() {
    return roomOption;
  }

  public void setRoomOption(RoomOptionResponseDTO roomOption) {
    this.roomOption = roomOption;
  }

  public VoteType getVoteType() {
    return voteType;
  }

  public void setVoteType(VoteType voteType) {
    this.voteType = voteType;
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