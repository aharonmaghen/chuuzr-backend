package com.chuuzr.chuuzrbackend.dto.auth;

import jakarta.validation.constraints.NotBlank;

import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidCountryCode;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidPhoneNumberFormat;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidPhoneNumberPair;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OTP request payload")
@ValidPhoneNumberPair
public class UserOtpRequest {
  @Schema(description = "2-letter country code", example = "US")
  @NotBlank(message = "Country code is required")
  @ValidCountryCode
  private final String countryCode;

  @Schema(description = "Phone number without country code", example = "5551234567")
  @NotBlank(message = "Phone number is required")
  @ValidPhoneNumberFormat
  private final String phoneNumber;

  public UserOtpRequest(
      @NotBlank(message = "Country code is required") @ValidCountryCode String countryCode,
      @NotBlank(message = "Phone number is required") @ValidPhoneNumberFormat String phoneNumber) {
    this.countryCode = countryCode;
    this.phoneNumber = phoneNumber;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
