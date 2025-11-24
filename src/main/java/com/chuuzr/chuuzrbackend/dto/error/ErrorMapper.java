package com.chuuzr.chuuzrbackend.dto.error;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

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

  public static ErrorDTO toErrorDTO(ErrorCode errorCode, String message, String path,
      Map<String, List<String>> validationErrors) {
    String resolvedMessage = message != null && !message.isBlank() ? message : errorCode.getDefaultMessage();
    ErrorDTO dto = new ErrorDTO();
    dto.setStatus(errorCode.getStatus().value());
    dto.setCode(errorCode.getCode());
    dto.setMessage(resolvedMessage);
    dto.setPath(path);
    dto.setTimestamp(Instant.now());
    dto.setValidationErrors(validationErrors);
    return dto;
  }
}
