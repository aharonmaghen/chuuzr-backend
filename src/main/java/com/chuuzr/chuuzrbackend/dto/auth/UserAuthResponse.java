package com.chuuzr.chuuzrbackend.dto.auth;

import java.util.UUID;

public class UserAuthResponse {
  private final String accessToken;
  private final UUID userUuid;
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
