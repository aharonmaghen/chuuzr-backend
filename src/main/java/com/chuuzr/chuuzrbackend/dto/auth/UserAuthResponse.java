package com.chuuzr.chuuzrbackend.dto.auth;

import java.util.UUID;

public class UserAuthResponse {
  private final String jwt;
  private final UUID userUuid;

  public UserAuthResponse(String jwt, UUID userUuid) {
    this.jwt = jwt;
    this.userUuid = userUuid;
  }

  public String getJwt() {
    return jwt;
  }

  public UUID getUserUuid() {
    return userUuid;
  }
}
