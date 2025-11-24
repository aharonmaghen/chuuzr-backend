package com.chuuzr.chuuzrbackend.util.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates that a nickname contains only letters, numbers, and underscores.
 * Only validates non-blank values (works with @NotBlank).
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface ValidNickname {

  String message() default "Nickname can only contain letters, numbers, and underscores";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
