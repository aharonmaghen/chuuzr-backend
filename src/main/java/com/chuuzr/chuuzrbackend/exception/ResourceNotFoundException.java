package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class ResourceNotFoundException extends BaseException {
  public ResourceNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public ResourceNotFoundException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public ResourceNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
