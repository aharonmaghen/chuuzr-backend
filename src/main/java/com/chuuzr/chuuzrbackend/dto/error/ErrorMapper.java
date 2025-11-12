package com.chuuzr.chuuzrbackend.dto.error;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

/**
 * Utility for constructing {@link ErrorDTO} instances.
 */
public final class ErrorMapper {

  private ErrorMapper() {
  }

  public static ErrorDTO toErrorDTO(ErrorCode errorCode, String message, String path) {
    String resolvedMessage = message != null && !message.isBlank() ? message : errorCode.getDefaultMessage();
    return toErrorDTO(errorCode.getStatus(), errorCode.getCode(), resolvedMessage, path);
  }

  public static ErrorDTO toErrorDTO(HttpStatus status, String code, String message, String path) {
    ErrorDTO dto = new ErrorDTO();
    dto.setStatus(status.value());
    dto.setCode(code);
    dto.setMessage(message);
    dto.setPath(path);
    dto.setTimestamp(Instant.now());
    return dto;
  }
}
