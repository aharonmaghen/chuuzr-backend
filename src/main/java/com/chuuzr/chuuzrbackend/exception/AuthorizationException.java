package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class AuthorizationException extends BaseException {
  public AuthorizationException(ErrorCode errorCode) {
    super(errorCode);
  }

  public AuthorizationException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public AuthorizationException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
