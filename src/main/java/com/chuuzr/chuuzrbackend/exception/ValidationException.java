package com.chuuzr.chuuzrbackend.exception;

import java.util.List;
import java.util.Map;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class ValidationException extends BaseException {

  private Map<String, List<String>> validationErrors;

  public ValidationException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public ValidationException(ErrorCode errorCode, Map<String, List<String>> validationErrors) {
    super(errorCode, "Validation failed for one or more fields");
    this.validationErrors = validationErrors;
  }

  public Map<String, List<String>> getValidationErrors() {
    return validationErrors;
  }

  public boolean hasValidationErrors() {
    return validationErrors != null && !validationErrors.isEmpty();
  }
}
