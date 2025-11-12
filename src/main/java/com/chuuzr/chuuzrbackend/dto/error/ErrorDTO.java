package com.chuuzr.chuuzrbackend.dto.error;

import java.time.Instant;

/**
 * Generic error payload returned by the API.
 */
public class ErrorDTO {
  private int status;
  private String code;
  private String message;
  private String path;
  private Instant timestamp;

  public ErrorDTO() {
  }

  public ErrorDTO(int status, String code, String message, String path, Instant timestamp) {
    this.status = status;
    this.code = code;
    this.message = message;
    this.path = path;
    this.timestamp = timestamp;
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
}
