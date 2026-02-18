package com.chuuzr.chuuzrbackend.dto.auth;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response with access token")
public class UserAuthResponse {
  @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private final String accessToken;

  @Schema(description = "Unique identifier for the authenticated user", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
  private final UUID userUuid;

  @Schema(description = "Whether the user needs to complete registration", example = "false")
  private final boolean requiresRegistration;

  public UserAuthResponse(String accessToken, UUID userUuid, boolean requiresRegistration) {
    this.accessToken = accessToken;
    this.userUuid = userUuid;
    this.requiresRegistration = requiresRegistration;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public UUID getUserUuid() {
    return userUuid;
  }

  public boolean isRequiresRegistration() {
    return requiresRegistration;
  }
}
