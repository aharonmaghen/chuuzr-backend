package com.chuuzr.chuuzrbackend.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public final class ValidationUtil {

  private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();

  private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{7,15}$");

  private static final Pattern COUNTRY_CODE_PATTERN = Pattern.compile("^[A-Z]{2}$");

  private static final Pattern OTP_PATTERN = Pattern.compile("^\\d{4,8}$");

  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s\\-']{2,50}$");

  private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,30}$");

  private static final Pattern API_PROVIDER_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{1,50}$");

  private static final Pattern EXTERNAL_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_.-]{1,100}$");

  private static final Pattern IMAGE_URL_PATTERN = Pattern
      .compile("^https?://.*\\.(jpg|jpeg|png|gif|webp|svg)(\\?.*)?$", Pattern.CASE_INSENSITIVE);

  private ValidationUtil() {
  }

  public static boolean isValidPhoneNumber(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
      return false;
    }
    return PHONE_NUMBER_PATTERN.matcher(phoneNumber.trim()).matches();
  }

  public static boolean isValidPhoneNumber(String phoneNumber, String countryCode) {
    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
      return false;
    }
    if (countryCode == null || countryCode.trim().isEmpty()) {
      return false;
    }
    try {
      PhoneNumber parsedNumber = PHONE_NUMBER_UTIL.parse(phoneNumber.trim(), countryCode.trim().toUpperCase());
      return PHONE_NUMBER_UTIL.isValidNumberForRegion(parsedNumber, countryCode.trim().toUpperCase());
    } catch (NumberParseException e) {
      return false;
    }
  }

  public static String formatPhoneNumber(String phoneNumber, String countryCode) {
    if (!isValidPhoneNumber(phoneNumber, countryCode)) {
      return null;
    }
    try {
      PhoneNumber parsedNumber = PHONE_NUMBER_UTIL.parse(phoneNumber.trim(), countryCode.trim().toUpperCase());
      return PHONE_NUMBER_UTIL.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
    } catch (NumberParseException e) {
      return null;
    }
  }

  public static String normalizePhoneNumber(String phoneNumber, String countryCode) {
    if (phoneNumber == null || phoneNumber.trim().isEmpty() || countryCode == null || countryCode.trim().isEmpty()) {
      return null;
    }

    String trimmedPhone = phoneNumber.trim();
    String normalizedCountry = countryCode.trim().toUpperCase();

    try {
      // Parse the phone number (Google's library handles leading 0 in many regions)
      PhoneNumber parsedNumber = PHONE_NUMBER_UTIL.parse(trimmedPhone, normalizedCountry);

      // Validate for the region
      if (!PHONE_NUMBER_UTIL.isValidNumberForRegion(parsedNumber, normalizedCountry)) {
        return null;
      }

      // Get the national number without country code (this strips the leading 0 for
      // many regions)
      long nationalNumber = parsedNumber.getNationalNumber();
      return String.valueOf(nationalNumber);
    } catch (NumberParseException e) {
      return null;
    }
  }

  public static boolean isValidCountryCode(String countryCode) {
    if (countryCode == null || countryCode.trim().isEmpty()) {
      return false;
    }
    String normalizedCode = countryCode.trim().toUpperCase();
    if (!COUNTRY_CODE_PATTERN.matcher(normalizedCode).matches()) {
      return false;
    }
    return PHONE_NUMBER_UTIL.getSupportedRegions().contains(normalizedCode);
  }

  public static java.util.Set<String> getSupportedCountryCodes() {
    return PHONE_NUMBER_UTIL.getSupportedRegions();
  }

  public static boolean isValidOtp(String otp) {
    if (otp == null || otp.trim().isEmpty()) {
      return false;
    }
    return OTP_PATTERN.matcher(otp.trim()).matches();
  }

  public static boolean isValidName(String name) {
    if (name == null || name.trim().isEmpty()) {
      return false;
    }
    return NAME_PATTERN.matcher(name.trim()).matches();
  }

  public static boolean isValidNickname(String nickname) {
    if (nickname == null || nickname.trim().isEmpty()) {
      return false;
    }
    return NICKNAME_PATTERN.matcher(nickname.trim()).matches();
  }

  public static boolean isValidApiProvider(String apiProvider) {
    if (apiProvider == null || apiProvider.trim().isEmpty()) {
      return false;
    }
    return API_PROVIDER_PATTERN.matcher(apiProvider.trim()).matches();
  }

  public static boolean isValidExternalId(String externalId) {
    if (externalId == null || externalId.trim().isEmpty()) {
      return false;
    }
    return EXTERNAL_ID_PATTERN.matcher(externalId.trim()).matches();
  }

  public static boolean isValidDescription(String description) {
    if (description == null) {
      return false;
    }
    String trimmed = description.trim();
    return trimmed.length() >= 1 && trimmed.length() <= 500;
  }

  public static boolean isValidRoomName(String name) {
    if (name == null) {
      return false;
    }
    String trimmed = name.trim();
    return trimmed.length() >= 1 && trimmed.length() <= 100;
  }

  public static boolean isValidOptionTypeName(String name) {
    if (name == null) {
      return false;
    }
    String trimmed = name.trim();
    return trimmed.length() >= 1 && trimmed.length() <= 50;
  }

  public static boolean isValidOptionName(String name) {
    if (name == null) {
      return false;
    }
    String trimmed = name.trim();
    return trimmed.length() >= 1 && trimmed.length() <= 100;
  }

  public static boolean isValidImageUrl(String imageUrl) {
    if (imageUrl == null || imageUrl.trim().isEmpty()) {
      return true;
    }
    return IMAGE_URL_PATTERN.matcher(imageUrl.trim()).matches();
  }

  public static boolean isValidUrl(String urlString) {
    if (urlString == null || urlString.trim().isEmpty()) {
      return false;
    }
    try {
      new URL(urlString.trim());
      return true;
    } catch (MalformedURLException e) {
      return false;
    }
  }

  public static boolean isNotBlank(String value) {
    return value != null && !value.trim().isEmpty();
  }

  public static boolean isValidLength(String value, int minLength, int maxLength) {
    if (value == null) {
      return false;
    }
    int length = value.trim().length();
    return length >= minLength && length <= maxLength;
  }
}
