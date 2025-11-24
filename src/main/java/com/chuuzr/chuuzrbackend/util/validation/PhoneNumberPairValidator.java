package com.chuuzr.chuuzrbackend.util.validation;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.chuuzr.chuuzrbackend.util.ValidationUtil;

/**
 * Validator for {@link ValidPhoneNumberPair} annotation.
 * Validates that a phone number is valid for the specified country code.
 */
public class PhoneNumberPairValidator implements ConstraintValidator<ValidPhoneNumberPair, Object> {

  private String phoneNumberField;
  private String countryCodeField;

  @Override
  public void initialize(ValidPhoneNumberPair constraintAnnotation) {
    this.phoneNumberField = constraintAnnotation.phoneNumberField();
    this.countryCodeField = constraintAnnotation.countryCodeField();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // Let @NotNull handle null checks
    }

    try {
      // Get the phone number field value
      Field phoneNumberFieldObj = value.getClass().getDeclaredField(phoneNumberField);
      phoneNumberFieldObj.setAccessible(true);
      String phoneNumber = (String) phoneNumberFieldObj.get(value);

      // Get the country code field value
      Field countryCodeFieldObj = value.getClass().getDeclaredField(countryCodeField);
      countryCodeFieldObj.setAccessible(true);
      String countryCode = (String) countryCodeFieldObj.get(value);

      // Validate using the country-specific validation
      boolean isValid = ValidationUtil.isValidPhoneNumber(phoneNumber, countryCode);

      if (!isValid) {
        // Customize the error message with the actual country code
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
          "Phone number is not valid for country: " + (countryCode != null ? countryCode.toUpperCase() : "UNKNOWN")
        ).addConstraintViolation();
      }

      return isValid;

    } catch (Exception e) {
      // If reflection fails, disable constraint validation for this field
      return false;
    }
  }
}
