package com.chuuzr.chuuzrbackend.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.chuuzr.chuuzrbackend.util.ValidationUtil;

public class ImageUrlValidator implements ConstraintValidator<ValidImageUrl, String> {

  @Override
  public void initialize(ValidImageUrl constraintAnnotation) {
  }

  @Override
  public boolean isValid(String imageUrl, ConstraintValidatorContext context) {
    return ValidationUtil.isValidImageUrl(imageUrl);
  }
}
