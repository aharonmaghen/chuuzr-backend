package com.chuuzr.chuuzrbackend.util.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validates that a name contains only letters, spaces, hyphens, and apostrophes.
 * Only validates non-blank values (works with @NotBlank).
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
public @interface ValidName {

  String message() default "Name can only contain letters, spaces, hyphens, and apostrophes";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
