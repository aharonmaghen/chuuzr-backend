package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public abstract class BaseException extends RuntimeException {
  private final ErrorCode errorCode;

  protected BaseException(ErrorCode errorCode) {
    super(errorCode.getDefaultMessage());
    this.errorCode = errorCode;
  }

  protected BaseException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  protected BaseException(ErrorCode errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
