package com.chuuzr.chuuzrbackend.dto.roomoption;

import java.util.UUID;

/**
 * DTO for adding an option to a room.
 * The room UUID comes from the path parameter.
 */
public class RoomOptionRequestDTO {
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
