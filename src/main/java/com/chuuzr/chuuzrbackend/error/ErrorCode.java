package com.chuuzr.chuuzrbackend.error;

import org.springframework.http.HttpStatus;

/**
 * Global catalog of API error codes to ensure consistent responses.
 */
public enum ErrorCode {
  AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", HttpStatus.UNAUTHORIZED, "Authentication failed");

  private final String code;
  private final HttpStatus status;
  private final String defaultMessage;

  ErrorCode(String code, HttpStatus status, String defaultMessage) {
    this.code = code;
    this.status = status;
    this.defaultMessage = defaultMessage;
  }

  public String getCode() {
    return code;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getDefaultMessage() {
    return defaultMessage;
  }
}
