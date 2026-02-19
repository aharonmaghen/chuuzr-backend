package com.chuuzr.chuuzrbackend.dto.uservote;

import com.chuuzr.chuuzrbackend.model.UserVote.VoteType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary vote response")
public class UserVoteSummaryResponseDTO {
  @Schema(description = "The recorded vote direction", example = "UP")
  private VoteType voteType;

  @Schema(description = "Updated voting score for this option in the room", example = "1")
  private Integer score;

  public UserVoteSummaryResponseDTO() {
  }

  public UserVoteSummaryResponseDTO(VoteType voteType, Integer score) {
    this.voteType = voteType;
    this.score = score;
  }

  public VoteType getVoteType() {
    return voteType;
  }

  public void setVoteType(VoteType voteType) {
    this.voteType = voteType;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }
}
