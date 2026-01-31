package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class RefreshTokenException extends BaseException {
  public RefreshTokenException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
