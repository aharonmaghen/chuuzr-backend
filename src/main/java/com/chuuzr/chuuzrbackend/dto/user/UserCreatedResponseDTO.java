package com.chuuzr.chuuzrbackend.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User creation response with authentication token")
public class UserCreatedResponseDTO {
  @Schema(description = "Authentication JWT token for the newly created user", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String token;

  @Schema(description = "User information")
  private UserResponseDTO user;

  public UserCreatedResponseDTO() {
  }

  public UserCreatedResponseDTO(String token, UserResponseDTO user) {
    this.token = token;
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserResponseDTO getUser() {
    return user;
  }

  public void setUser(UserResponseDTO user) {
    this.user = user;
  }
}
