package com.chuuzr.chuuzrbackend.exception.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.chuuzr.chuuzrbackend.dto.error.ErrorDTO;
import com.chuuzr.chuuzrbackend.dto.error.ErrorMapper;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.BaseException;
import com.chuuzr.chuuzrbackend.exception.ValidationException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ValidationException.class)
  public ErrorDTO handleValidationException(ValidationException ex, HttpServletRequest request) {
    logger.warn("Validation exception at {}: {}", request.getRequestURI(), ex.getMessage());
    if (ex.hasValidationErrors()) {
      return ErrorMapper.toErrorDTO(ex.getErrorCode(), ex.getMessage(), request.getRequestURI(),
          ex.getValidationErrors());
    }
    return ErrorMapper.toErrorDTO(ex.getErrorCode(), ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(BaseException.class)
  public ErrorDTO handleBaseException(BaseException ex, HttpServletRequest request) {
    logger.warn("Base exception at {}: {}", request.getRequestURI(), ex.getMessage());
    return ErrorMapper.toErrorDTO(ex.getErrorCode(), ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ErrorDTO handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
    logger.warn("Response status exception at {}: {}", request.getRequestURI(), ex.getMessage());
    HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
    if (status == null) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    String code = status.name();
    String message = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();
    return ErrorMapper.toErrorDTO(status, code, message, request.getRequestURI());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ErrorDTO handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
    logger.warn("Illegal argument at {}: {}", request.getRequestURI(), ex.getMessage());
    return ErrorMapper.toErrorDTO(ErrorCode.INVALID_INPUT, ex.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler({
      io.jsonwebtoken.ExpiredJwtException.class,
      io.jsonwebtoken.MalformedJwtException.class,
      io.jsonwebtoken.security.SignatureException.class,
      io.jsonwebtoken.UnsupportedJwtException.class,
      io.jsonwebtoken.security.SecurityException.class
  })
  public ErrorDTO handleJwtException(Exception ex, HttpServletRequest request) {
    logger.warn("JWT exception at {}", request.getRequestURI());
    return ErrorMapper.toErrorDTO(ErrorCode.JWT_INVALID, null, request.getRequestURI());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ErrorDTO handleDataIntegrityViolationException(DataIntegrityViolationException ex,
      HttpServletRequest request) {
    logger.warn("Data integrity violation at {}", request.getRequestURI());
    String message = ex.getMessage();
    if (message != null && (message.contains("duplicate") || message.contains("unique"))) {
      return ErrorMapper.toErrorDTO(ErrorCode.DUPLICATE_RESOURCE, "Resource already exists",
          request.getRequestURI());
    }
    return ErrorMapper.toErrorDTO(ErrorCode.INVALID_INPUT, "Data integrity violation", request.getRequestURI());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    logger.warn("Method argument validation failed at {}", request.getRequestURI());
    Map<String, List<String>> validationErrors = new HashMap<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      validationErrors.computeIfAbsent(error.getField(), k -> new ArrayList<>()).add(error.getDefaultMessage());
    }

    boolean hasCountryCodeErrors = validationErrors.containsKey("countryCode");

    if (!hasCountryCodeErrors) {
      ex.getBindingResult().getGlobalErrors().forEach(error -> {
        validationErrors.computeIfAbsent("phoneNumber", k -> new ArrayList<>()).add(error.getDefaultMessage());
      });
    }

    return ErrorMapper.toErrorDTO(ErrorCode.VALIDATION_FAILED,
        "Validation failed for one or more fields", request.getRequestURI(), validationErrors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ErrorDTO handleConstraintViolationException(ConstraintViolationException ex,
      HttpServletRequest request) {
    logger.warn("Constraint violation at {}", request.getRequestURI());
    Map<String, List<String>> validationErrors = new HashMap<>();

    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      String propertyPath = violation.getPropertyPath().toString();
      String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);

      validationErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(violation.getMessage());
    }

    return ErrorMapper.toErrorDTO(ErrorCode.CONSTRAINT_VIOLATION,
        "Constraint violation occurred", request.getRequestURI(), validationErrors);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ErrorDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex,
      HttpServletRequest request) {
    logger.warn("Method not supported at {}: {}", request.getRequestURI(), ex.getMethod());
    String message = String.format("Method '%s' is not supported for this endpoint", ex.getMethod());
    return ErrorMapper.toErrorDTO(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", message,
        request.getRequestURI());
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ErrorDTO handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex,
      HttpServletRequest request) {
    logger.warn("Media type not supported at {}", request.getRequestURI());
    String message = String.format("Media type '%s' is not supported", ex.getContentType());
    return ErrorMapper.toErrorDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "UNSUPPORTED_MEDIA_TYPE", message,
        request.getRequestURI());
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ErrorDTO handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
      HttpServletRequest request) {
    logger.warn("Missing request parameter at {}: {}", request.getRequestURI(), ex.getParameterName());
    String message = String.format("Required parameter '%s' is missing", ex.getParameterName());
    return ErrorMapper.toErrorDTO(ErrorCode.INVALID_INPUT, message, request.getRequestURI());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ErrorDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
      HttpServletRequest request) {
    logger.warn("Malformed request body at {}", request.getRequestURI());
    return ErrorMapper.toErrorDTO(ErrorCode.INVALID_INPUT, "Malformed request body", request.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  public ErrorDTO handleGenericException(Exception ex, HttpServletRequest request) {
    logger.error("Unhandled exception at {}", request.getRequestURI(), ex);
    return ErrorMapper.toErrorDTO(ErrorCode.INTERNAL_SERVER_ERROR, null, request.getRequestURI());
  }
}
