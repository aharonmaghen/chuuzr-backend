package com.chuuzr.chuuzrbackend.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class CountryCodeUtil {
  private static final Map<String, String> COUNTRY_TO_DIAL_CODE;
  private static final Map<String, String> DIAL_CODE_TO_COUNTRY;

  static {
    Map<String, String> countryToDial = new HashMap<>();
    countryToDial.put("US", "1");
    countryToDial.put("IL", "972");
    COUNTRY_TO_DIAL_CODE = Collections.unmodifiableMap(countryToDial);

    Map<String, String> dialToCountry = new HashMap<>();
    for (Map.Entry<String, String> entry : COUNTRY_TO_DIAL_CODE.entrySet()) {
      dialToCountry.put(entry.getValue(), entry.getKey());
    }
    DIAL_CODE_TO_COUNTRY = Collections.unmodifiableMap(dialToCountry);
  }

  private CountryCodeUtil() {
  }

  public static String toDialCode(String alpha2CountryCode) {
    if (alpha2CountryCode == null) {
      throw new IllegalArgumentException("Country code cannot be null");
    }

    String dialCode = COUNTRY_TO_DIAL_CODE.get(alpha2CountryCode.toUpperCase());
    if (dialCode == null) {
      throw new IllegalArgumentException(
          String.format("Unsupported country code: %s", alpha2CountryCode));
    }
    return dialCode;
  }

  public static String toAlpha2(String dialCode) {
    if (dialCode == null) {
      throw new IllegalArgumentException("Dial code cannot be null");
    }

    String alpha2 = DIAL_CODE_TO_COUNTRY.get(dialCode);
    if (alpha2 == null) {
      throw new IllegalArgumentException(String.format("Unsupported dial code: %s", dialCode));
    }
    return alpha2;
  }
}
