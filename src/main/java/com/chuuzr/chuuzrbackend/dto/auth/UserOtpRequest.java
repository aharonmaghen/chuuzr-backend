package com.chuuzr.chuuzrbackend.dto.auth;

import jakarta.validation.constraints.NotBlank;

import com.chuuzr.chuuzrbackend.util.validation.ValidCountryCode;
import com.chuuzr.chuuzrbackend.util.validation.ValidPhoneNumberPair;

@ValidPhoneNumberPair
public class UserOtpRequest {
  @NotBlank(message = "Country code is required")
  @ValidCountryCode
  private final String countryCode;

  @NotBlank(message = "Phone number is required")
  private final String phoneNumber;

  public UserOtpRequest(
      @NotBlank(message = "Country code is required") @ValidCountryCode String countryCode,
      @NotBlank(message = "Phone number is required") String phoneNumber) {
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
