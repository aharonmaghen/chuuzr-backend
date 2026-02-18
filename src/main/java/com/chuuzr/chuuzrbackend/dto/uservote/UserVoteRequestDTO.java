package com.chuuzr.chuuzrbackend.dto.uservote;

import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Vote request payload")
public class UserVoteRequestDTO {
  @Schema(description = "The vote direction", example = "UP")
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
