package com.chuuzr.chuuzrbackend.dto.auth;

import jakarta.validation.constraints.NotBlank;

import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidCountryCode;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidPhoneNumberFormat;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidPhoneNumberPair;

@ValidPhoneNumberPair
public class UserOtpRequest {
  @NotBlank(message = "Country code is required")
  @ValidCountryCode
  private final String countryCode;

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
