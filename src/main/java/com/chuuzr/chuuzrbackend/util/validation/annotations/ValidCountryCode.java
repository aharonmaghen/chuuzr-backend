package com.chuuzr.chuuzrbackend.util.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.chuuzr.chuuzrbackend.util.validation.validators.CountryCodeValidator;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryCodeValidator.class)
public @interface ValidCountryCode {

  String message() default "Country code must be a valid 2-letter country code (ISO 3166-1 alpha-2 standard)";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
