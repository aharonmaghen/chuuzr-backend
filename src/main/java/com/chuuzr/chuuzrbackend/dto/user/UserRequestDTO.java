package com.chuuzr.chuuzrbackend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidCountryCode;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidImageUrl;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidName;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidNickname;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidPhoneNumberFormat;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidPhoneNumberPair;

import io.swagger.v3.oas.annotations.media.Schema;

@ValidPhoneNumberPair
public class UserRequestDTO {
  @Schema(description = "User's full name", example = "John Doe", minLength = 2, maxLength = 50)
  @NotBlank(message = "Name is required")
  @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
  @ValidName
  private String name;

  @Schema(description = "User's unique nickname", example = "johndoe123", minLength = 3, maxLength = 30)
  @NotBlank(message = "Nickname is required")
  @Size(min = 3, max = 30, message = "Nickname must be between 3 and 30 characters")
  @ValidNickname
  private String nickname;

  @Schema(description = "2-letter country code supported by phone number validation", example = "US", minLength = 2, maxLength = 2)
  @NotBlank(message = "Country code is required")
  @ValidCountryCode
  private String countryCode;

  @Schema(description = "Phone number without country code (leading 0 will be stripped automatically)", example = "5551234567", minLength = 7, maxLength = 15)
  @NotBlank(message = "Phone number is required")
  @ValidPhoneNumberFormat
  private String phoneNumber;

  @Schema(description = "URL to user's profile picture", example = "https://example.com/profile.jpg", format = "uri")
  @ValidImageUrl
  private String profilePicture;

  public UserRequestDTO() {
  }

  public UserRequestDTO(String name, String nickname, String countryCode, String phoneNumber, String profilePicture) {
    this.name = name;
    this.nickname = nickname;
    this.countryCode = countryCode;
    this.phoneNumber = phoneNumber;
    this.profilePicture = profilePicture;
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

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }
}
