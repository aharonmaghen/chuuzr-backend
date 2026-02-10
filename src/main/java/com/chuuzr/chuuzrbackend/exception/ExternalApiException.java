package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class ExternalApiException extends BaseException {
  public ExternalApiException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public ExternalApiException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
