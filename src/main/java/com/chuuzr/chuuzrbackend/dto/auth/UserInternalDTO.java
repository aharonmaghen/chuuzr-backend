package com.chuuzr.chuuzrbackend.dto.auth;

import java.util.Set;
import java.util.UUID;

public class UserInternalDTO {
  private final UUID uuid;
  private final String name;
  private final String nickname;
  private final Set<String> roles;

  public UserInternalDTO(UUID uuid, String name, String nickname, Set<String> roles) {
    this.uuid = uuid;
    this.name = name;
    this.nickname = nickname;
    this.roles = roles;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }

  public String getNickname() {
    return nickname;
  }

  public Set<String> getRoles() {
    return roles;
  }
}
