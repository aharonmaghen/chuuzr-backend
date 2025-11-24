package com.chuuzr.chuuzrbackend.util.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryCodeValidator.class)
public @interface ValidCountryCode {

  String message() default "Country code must be a valid 2-letter country code supported by phone number validation";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
