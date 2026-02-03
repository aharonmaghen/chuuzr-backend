package com.chuuzr.chuuzrbackend.service.sms;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.chuuzr.chuuzrbackend.util.CountryCodeUtil;
import com.chuuzr.chuuzrbackend.util.PiiMaskingUtil;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.SmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Profile("!dev")
public class TwilioService implements SmsService {
  private static final Logger logger = LoggerFactory.getLogger(TwilioService.class);

  private final String fromPhoneNumber;

  public TwilioService(
      @Value("${twilio.account-sid}") String accountSid,
      @Value("${twilio.auth-token}") String authToken,
      @Value("${twilio.from-phone-number}") String fromPhoneNumber) {
    Twilio.init(accountSid, authToken);
    this.fromPhoneNumber = fromPhoneNumber;
  }

  @Override
  public void sendOtp(String countryCode, String phoneNumber, String otp) {
    String dialCode = CountryCodeUtil.toDialCode(countryCode);
    String toPhoneNumber = "+" + dialCode + phoneNumber;
    String messageBody = String.format("Your verification code is %s", otp);

    logger.debug("Sending OTP via Twilio to phone: {}",
        PiiMaskingUtil.maskPhoneNumberWithCountry(phoneNumber, countryCode));

    try {
      Message.creator(
          new PhoneNumber(toPhoneNumber),
          new PhoneNumber(fromPhoneNumber),
          messageBody).create();
      logger.info("OTP sent successfully to phone: {}",
          PiiMaskingUtil.maskPhoneNumberWithCountry(phoneNumber, countryCode));
    } catch (ApiException ex) {
      logger.warn("Failed to send OTP via Twilio to phone: {} - error: {}",
          PiiMaskingUtil.maskPhoneNumberWithCountry(phoneNumber, countryCode), ex.getMessage());
      throw new SmsException(ErrorCode.SMS_SEND_FAILED, "Failed to send OTP via Twilio: " + ex.getMessage(), ex);
    }
  }
}
