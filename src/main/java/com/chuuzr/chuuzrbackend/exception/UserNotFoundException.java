package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class UserNotFoundException extends BaseException {
  public UserNotFoundException() {
    super(ErrorCode.USER_NOT_FOUND);
  }

  public UserNotFoundException(String message) {
    super(ErrorCode.USER_NOT_FOUND, message);
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(ErrorCode.USER_NOT_FOUND, message, cause);
  }
}
