package com.chuuzr.chuuzrbackend.dto.uservote;

import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;

import jakarta.validation.constraints.NotNull;

public class UserVoteRequestDTO {
  @NotNull(message = "voteType is required")
  private VoteType voteType;

  public UserVoteRequestDTO() {
  }

  public UserVoteRequestDTO(VoteType voteType) {
    this.voteType = voteType;
  }

  public VoteType getVoteType() {
    return voteType;
  }

  public void setVoteType(VoteType voteType) {
    this.voteType = voteType;
  }
}
