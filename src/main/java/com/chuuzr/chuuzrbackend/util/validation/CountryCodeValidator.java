package com.chuuzr.chuuzrbackend.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.chuuzr.chuuzrbackend.util.ValidationUtil;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {

  @Override
  public void initialize(ValidCountryCode constraintAnnotation) {
  }

  @Override
  public boolean isValid(String countryCode, ConstraintValidatorContext context) {
    return ValidationUtil.isValidCountryCode(countryCode);
  }
}
