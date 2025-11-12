package com.chuuzr.chuuzrbackend.dto.auth;

public class UserOtpVerifyRequest {
  private final String countryCode;
  private final String phoneNumber;
  private final String otp;

  public UserOtpVerifyRequest(String countryCode, String phoneNumber, String otp) {
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
