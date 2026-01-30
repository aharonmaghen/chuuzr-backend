package com.chuuzr.chuuzrbackend.util.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import com.chuuzr.chuuzrbackend.util.validation.validators.PhoneNumberFormatValidator;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberFormatValidator.class)
public @interface ValidPhoneNumberFormat {
  String message() default "Phone number must be in valid format (only digits)";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
