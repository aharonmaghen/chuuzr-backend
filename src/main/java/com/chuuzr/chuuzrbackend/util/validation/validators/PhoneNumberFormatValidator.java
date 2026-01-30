package com.chuuzr.chuuzrbackend.util.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidPhoneNumberFormat;

public class PhoneNumberFormatValidator implements ConstraintValidator<ValidPhoneNumberFormat, String> {

  @Override
  public void initialize(ValidPhoneNumberFormat annotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    if (!value.matches("\\d+")) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Phone number must contain only digits")
          .addConstraintViolation();
      return false;
    }

    return true;
  }
}
