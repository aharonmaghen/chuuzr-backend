package com.chuuzr.chuuzrbackend.util.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for {@link ValidNickname} annotation.
 * Validates that a nickname contains only letters, numbers, and underscores.
 * Only validates non-blank values (defers to @NotBlank for blank validation).
 */
public class NicknameValidator implements ConstraintValidator<ValidNickname, String> {

  private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

  @Override
  public void initialize(ValidNickname constraintAnnotation) {
    // No initialization needed
  }

  @Override
  public boolean isValid(String nickname, ConstraintValidatorContext context) {
    // If the nickname is null or blank, let @NotBlank handle it
    if (nickname == null || nickname.trim().isEmpty()) {
      return true;
    }

    // Only validate the pattern for non-blank values
    return NICKNAME_PATTERN.matcher(nickname.trim()).matches();
  }
}
