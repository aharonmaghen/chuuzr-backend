package com.chuuzr.chuuzrbackend.dto.user;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for returning User data to API clients.
 * Only exposes UUID (not internal ID) and includes timestamps.
 */
public class UserResponseDTO {
  private UUID uuid;
  private String name;
  private String nickname;
  private String countryCode;
  private String phoneNumber;
  private URL profilePicture;
  private LocalDateTime updatedAt;
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
