package com.chuuzr.chuuzrbackend.util;

public class PiiMaskingUtil {

  private static final String MASK_CHARACTER = "*";
  private static final int VISIBLE_SUFFIX_LENGTH = 2;

  public static String maskPhoneNumber(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.isEmpty()) {
      return "[INVALID]";
    }

    int length = phoneNumber.length();
    if (length <= VISIBLE_SUFFIX_LENGTH) {
      return MASK_CHARACTER.repeat(length);
    }

    String suffix = phoneNumber.substring(length - VISIBLE_SUFFIX_LENGTH);
    String masked = MASK_CHARACTER.repeat(length - VISIBLE_SUFFIX_LENGTH) + suffix;

    return masked;
  }

  public static String maskEmail(String email) {
    if (email == null || !email.contains("@")) {
      return "[INVALID]";
    }

    int atIndex = email.indexOf("@");
    if (atIndex <= 0) {
      return "[INVALID]";
    }

    String domain = email.substring(atIndex);
    return MASK_CHARACTER.repeat(atIndex) + domain;
  }

  public static String maskOtp(String otp) {
    if (otp == null || otp.isEmpty()) {
      return "[OTP:0]";
    }

    return "[OTP:" + otp.length() + "]";
  }

  public static String maskToken(String token) {
    if (token == null || token.isEmpty()) {
      return "[INVALID]";
    }

    int length = token.length();
    if (length <= 12) {
      return MASK_CHARACTER.repeat(length);
    }

    String prefix = token.substring(0, 8);
    String suffix = token.substring(length - 4);
    int maskedLength = length - 12;

    return prefix + MASK_CHARACTER.repeat(maskedLength) + suffix;
  }

  public static String maskPhoneNumberWithCountry(String phoneNumber, String countryCode) {
    if (phoneNumber == null || countryCode == null) {
      return "[INVALID]";
    }

    return "(" + countryCode + ") " + maskPhoneNumber(phoneNumber);
  }
}
