package com.chuuzr.chuuzrbackend.service.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TwilioService implements SmsService {
  private static final Logger log = LoggerFactory.getLogger(TwilioService.class);

  @Override
  public void sendOtp(String countryCode, String phoneNumber, String otp) {
    log.info("Mock SMS: sending OTP {} to {}{}", otp, countryCode, phoneNumber);
  }
}
