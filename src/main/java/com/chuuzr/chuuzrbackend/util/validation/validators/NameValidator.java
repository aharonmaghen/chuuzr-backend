package com.chuuzr.chuuzrbackend.util.validation.validators;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidName;

/**
 * Validator for {@link ValidName} annotation.
 * Validates that a name contains only letters, spaces, hyphens, and apostrophes.
 * Only validates non-blank values (defers to @NotBlank for blank validation).
 */
public class NameValidator implements ConstraintValidator<ValidName, String> {

  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s\\-']+$");

  @Override
  public void initialize(ValidName constraintAnnotation) {
    // No initialization needed
  }

  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    // If the name is null or blank, let @NotBlank handle it
    if (name == null || name.trim().isEmpty()) {
      return true;
    }

    // Only validate the pattern for non-blank values
    return NAME_PATTERN.matcher(name.trim()).matches();
  }
}
