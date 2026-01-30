package com.chuuzr.chuuzrbackend.util.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.chuuzr.chuuzrbackend.util.validation.validators.PhoneNumberPairValidator;

/**
 * Validates that a phone number is valid for the given country code.
 * This annotation should be placed at the class level and requires both
 * phoneNumber and countryCode fields to be present in the validated class.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberPairValidator.class)
public @interface ValidPhoneNumberPair {

  String message() default "Phone number is not valid for country: {countryCode}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * The name of the field containing the phone number.
   * Defaults to "phoneNumber".
   */
  String phoneNumberField() default "phoneNumber";

  /**
   * The name of the field containing the country code.
   * Defaults to "countryCode".
   */
  String countryCodeField() default "countryCode";
}
