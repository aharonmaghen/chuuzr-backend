package com.chuuzr.chuuzrbackend.dto.roomoption;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class RoomOptionRequestDTO {
  @NotNull(message = "Option UUID is required")
  private UUID optionUuid;

  public RoomOptionRequestDTO() {
  }

  public RoomOptionRequestDTO(UUID optionUuid) {
    this.optionUuid = optionUuid;
  }

  public UUID getOptionUuid() {
    return optionUuid;
  }

  public void setOptionUuid(UUID optionUuid) {
    this.optionUuid = optionUuid;
  }
}
