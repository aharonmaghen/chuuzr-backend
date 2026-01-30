package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class SmsException extends BaseException {
  public SmsException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public SmsException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
