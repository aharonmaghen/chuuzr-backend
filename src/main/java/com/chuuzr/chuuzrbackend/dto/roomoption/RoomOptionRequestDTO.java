package com.chuuzr.chuuzrbackend.dto.roomoption;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to add an option to a room")
public class RoomOptionRequestDTO {
  @Schema(description = "UUID of the option to add", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
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
