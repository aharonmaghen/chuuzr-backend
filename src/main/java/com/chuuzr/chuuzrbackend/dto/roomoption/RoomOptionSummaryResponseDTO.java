package com.chuuzr.chuuzrbackend.dto.roomoption;

import com.chuuzr.chuuzrbackend.dto.option.OptionSummaryResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary room-option response for list contexts")
public class RoomOptionSummaryResponseDTO {

  @Schema(description = "The option in this association")
  private OptionSummaryResponseDTO option;

  @Schema(description = "Voting score for this option in the room", example = "5")
  private Integer score;

  public RoomOptionSummaryResponseDTO() {
  }

  public RoomOptionSummaryResponseDTO(OptionSummaryResponseDTO option, Integer score) {
    this.option = option;
    this.score = score;
  }

  public OptionSummaryResponseDTO getOption() {
    return option;
  }

  public void setOption(OptionSummaryResponseDTO option) {
    this.option = option;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }
}
