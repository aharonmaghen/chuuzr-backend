package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class BusinessLogicException extends BaseException {
  public BusinessLogicException(ErrorCode errorCode) {
    super(errorCode);
  }

  public BusinessLogicException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public BusinessLogicException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
