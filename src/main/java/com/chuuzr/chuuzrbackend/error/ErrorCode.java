package com.chuuzr.chuuzrbackend.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  // Authentication & Authorization
  AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", HttpStatus.UNAUTHORIZED, "Authentication failed"),
  OTP_INVALID("OTP_INVALID", HttpStatus.UNAUTHORIZED, "Invalid authentication credentials"),
  JWT_INVALID("JWT_INVALID", HttpStatus.UNAUTHORIZED, "Invalid authentication token"),
  AUTHORIZATION_FAILED("AUTHORIZATION_FAILED", HttpStatus.FORBIDDEN, "Authorization failed"),
  FORBIDDEN_OPERATION("FORBIDDEN_OPERATION", HttpStatus.FORBIDDEN, "Operation not allowed"),

  // Resource Not Found
  RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, "Resource not found"),
  USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User not found"),
  ROOM_NOT_FOUND("ROOM_NOT_FOUND", HttpStatus.NOT_FOUND, "Room not found"),
  ROOM_USER_NOT_FOUND("ROOM_USER_NOT_FOUND", HttpStatus.NOT_FOUND, "Room user not found"),
  ROOM_OPTION_NOT_FOUND("ROOM_OPTION_NOT_FOUND", HttpStatus.NOT_FOUND, "Room option not found"),
  OPTION_NOT_FOUND("OPTION_NOT_FOUND", HttpStatus.NOT_FOUND, "Option not found"),
  OPTION_TYPE_NOT_FOUND("OPTION_TYPE_NOT_FOUND", HttpStatus.NOT_FOUND, "Option type not found"),

  // Validation & Input Errors
  INVALID_INPUT("INVALID_INPUT", HttpStatus.BAD_REQUEST, "Invalid input provided"),
  VALIDATION_FAILED("VALIDATION_FAILED", HttpStatus.BAD_REQUEST, "Validation failed for one or more fields"),
  FIELD_REQUIRED("FIELD_REQUIRED", HttpStatus.BAD_REQUEST, "Required field is missing"),
  FIELD_INVALID_FORMAT("FIELD_INVALID_FORMAT", HttpStatus.BAD_REQUEST, "Field has invalid format"),
  FIELD_TOO_SHORT("FIELD_TOO_SHORT", HttpStatus.BAD_REQUEST, "Field value is too short"),
  FIELD_TOO_LONG("FIELD_TOO_LONG", HttpStatus.BAD_REQUEST, "Field value is too long"),
  FIELD_INVALID_VALUE("FIELD_INVALID_VALUE", HttpStatus.BAD_REQUEST, "Field contains invalid value"),
  CONSTRAINT_VIOLATION("CONSTRAINT_VIOLATION", HttpStatus.BAD_REQUEST, "Constraint violation occurred"),

  // Conflict Errors
  VOTING_CONFLICT("VOTING_CONFLICT", HttpStatus.CONFLICT, "Voting conflict occurred"),
  INVALID_VOTE_TRANSITION("INVALID_VOTE_TRANSITION", HttpStatus.BAD_REQUEST, "Invalid vote transition"),
  DUPLICATE_RESOURCE("DUPLICATE_RESOURCE", HttpStatus.CONFLICT, "Resource already exists"),

  // Server Errors
  INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred");

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
