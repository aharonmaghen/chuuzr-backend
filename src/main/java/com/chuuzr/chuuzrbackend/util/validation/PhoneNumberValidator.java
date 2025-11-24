package com.chuuzr.chuuzrbackend.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.chuuzr.chuuzrbackend.util.ValidationUtil;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

  @Override
  public void initialize(ValidPhoneNumber constraintAnnotation) {
  }

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
    return ValidationUtil.isValidPhoneNumber(phoneNumber);
  }
}
