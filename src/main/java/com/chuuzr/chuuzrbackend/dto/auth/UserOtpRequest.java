package com.chuuzr.chuuzrbackend.dto.auth;

public class UserOtpRequest {
  private final String countryCode;
  private final String phoneNumber;

  public UserOtpRequest(String countryCode, String phoneNumber) {
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
