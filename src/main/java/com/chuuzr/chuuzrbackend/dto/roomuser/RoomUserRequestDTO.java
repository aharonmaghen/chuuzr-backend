package com.chuuzr.chuuzrbackend.dto.roomuser;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to add a user to a room")
public class RoomUserRequestDTO {
  @Schema(description = "UUID of the user to add", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
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
