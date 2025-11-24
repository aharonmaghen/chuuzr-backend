package com.chuuzr.chuuzrbackend.dto.user;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User information response")
public class UserResponseDTO {
  @Schema(description = "Unique identifier for the user", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
  private UUID uuid;

  @Schema(description = "User's full name", example = "John Doe")
  private String name;

  @Schema(description = "User's unique nickname", example = "johndoe123")
  private String nickname;

  @Schema(description = "2-letter country code supported by phone number validation", example = "US")
  private String countryCode;

  @Schema(description = "Phone number without country code", example = "5551234567")
  private String phoneNumber;

  @Schema(description = "URL to user's profile picture", example = "https://example.com/profile.jpg", format = "uri")
  private URL profilePicture;

  @Schema(description = "Last update timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime updatedAt;

  @Schema(description = "Creation timestamp", example = "2024-01-15T10:30:00", format = "date-time")
  private LocalDateTime createdAt;

  public UserResponseDTO() {
  }

  public UserResponseDTO(UUID uuid, String name, String nickname, String countryCode, String phoneNumber,
      URL profilePicture, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.uuid = uuid;
    this.name = name;
    this.nickname = nickname;
    this.countryCode = countryCode;
    this.phoneNumber = phoneNumber;
    this.profilePicture = profilePicture;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public URL getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(URL profilePicture) {
    this.profilePicture = profilePicture;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
