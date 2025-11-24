package com.chuuzr.chuuzrbackend.util.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageUrlValidator.class)
public @interface ValidImageUrl {

  String message() default "Invalid image URL format";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
