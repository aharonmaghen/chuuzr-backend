package com.chuuzr.chuuzrbackend.service.sms;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MockSmsService implements SmsService {
  @Override
  public void sendOtp(String countryCode, String phoneNumber, String otp) {
  }
}
