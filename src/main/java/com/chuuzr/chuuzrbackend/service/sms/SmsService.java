package com.chuuzr.chuuzrbackend.service.sms;

public interface SmsService {
  void sendOtp(String countryCode, String phoneNumber, String otp);
}
