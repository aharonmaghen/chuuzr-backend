package com.chuuzr.chuuzrbackend.dto.error;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API error response")
public class ErrorDTO {
  @Schema(description = "HTTP status code", example = "400")
  private int status;

  @Schema(description = "Error code identifier", example = "INVALID_INPUT")
  private String code;

  @Schema(description = "Human-readable error message", example = "Validation failed for one or more fields")
  private String message;

  @Schema(description = "Request path that caused the error", example = "/api/users")
  private String path;

  @Schema(description = "Timestamp when the error occurred", format = "date-time")
  private Instant timestamp;

  @Schema(description = "Validation errors grouped by field name (present for validation errors)")
  private Map<String, List<String>> validationErrors;

  public ErrorDTO() {
  }

  public ErrorDTO(int status, String code, String message, String path, Instant timestamp) {
    this.status = status;
    this.code = code;
    this.message = message;
    this.path = path;
    this.timestamp = timestamp;
  }

  public ErrorDTO(int status, String code, String message, String path, Instant timestamp,
      Map<String, List<String>> validationErrors) {
    this.status = status;
    this.code = code;
    this.message = message;
    this.path = path;
    this.timestamp = timestamp;
    this.validationErrors = validationErrors;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public Map<String, List<String>> getValidationErrors() {
    return validationErrors;
  }

  public void setValidationErrors(Map<String, List<String>> validationErrors) {
    this.validationErrors = validationErrors;
  }
}
