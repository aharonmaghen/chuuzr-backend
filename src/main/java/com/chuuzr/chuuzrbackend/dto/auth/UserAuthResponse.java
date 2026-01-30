package com.chuuzr.chuuzrbackend.dto.auth;

import java.util.UUID;

public class UserAuthResponse {
  private final String jwt;
  private final UUID userUuid;
  private final boolean requiresRegistration;

  public UserAuthResponse(String jwt, UUID userUuid, boolean requiresRegistration) {
    this.jwt = jwt;
    this.userUuid = userUuid;
    this.requiresRegistration = requiresRegistration;
  }

  public String getJwt() {
    return jwt;
  }

  public UUID getUserUuid() {
    return userUuid;
  }

  public boolean isRequiresRegistration() {
    return requiresRegistration;
  }
}
