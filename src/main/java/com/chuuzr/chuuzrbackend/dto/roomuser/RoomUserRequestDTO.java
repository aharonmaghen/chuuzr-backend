package com.chuuzr.chuuzrbackend.dto.roomuser;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class RoomUserRequestDTO {
  @NotNull(message = "User UUID is required")
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
