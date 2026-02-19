package com.chuuzr.chuuzrbackend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidCountryCode;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidPhoneNumberPair;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OTP verification request payload")
@ValidPhoneNumberPair
public class UserOtpVerifyRequest {
  @Schema(description = "2-letter country code", example = "US")
  @NotBlank(message = "Country code is required")
  @ValidCountryCode
  private final String countryCode;

  @Schema(description = "Phone number without country code", example = "5551234567")
  @NotBlank(message = "Phone number is required")
  private final String phoneNumber;

  @Schema(description = "One-time password sent via SMS", example = "111111")
  @NotBlank(message = "OTP is required")
  @Pattern(regexp = "^\\d{4,8}$", message = "OTP must be 4-8 digits")
  private final String otp;

  public UserOtpVerifyRequest(
      @NotBlank(message = "Country code is required") @ValidCountryCode String countryCode,
      @NotBlank(message = "Phone number is required") String phoneNumber,
      @NotBlank(message = "OTP is required") @Pattern(regexp = "^\\d{4,8}$", message = "OTP must be 4-8 digits") String otp) {
    this.countryCode = countryCode;
    this.phoneNumber = phoneNumber;
    this.otp = otp;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getOtp() {
    return otp;
  }
}
