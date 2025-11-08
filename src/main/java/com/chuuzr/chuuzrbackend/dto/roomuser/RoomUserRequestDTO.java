package com.chuuzr.chuuzrbackend.dto.roomuser;

import java.util.UUID;

/**
 * DTO for adding a user to a room.
 * The room UUID comes from the path parameter.
 */
public class RoomUserRequestDTO {
  private UUID userUuid;

  public RoomUserRequestDTO() {
  }

  public RoomUserRequestDTO(UUID userUuid) {
    this.userUuid = userUuid;
  }

  public UUID getUserUuid() {
    return userUuid;
  }

  public void setUserUuid(UUID userUuid) {
    this.userUuid = userUuid;
  }
}
