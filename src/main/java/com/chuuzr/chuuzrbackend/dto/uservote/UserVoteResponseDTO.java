package com.chuuzr.chuuzrbackend.dto.uservote;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionResponseDTO;
import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserResponseDTO;
import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;

public class UserVoteResponseDTO {
  private RoomUserResponseDTO roomUser;
  private RoomOptionResponseDTO roomOption;
  private VoteType voteType;
  private LocalDateTime createdAt;
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