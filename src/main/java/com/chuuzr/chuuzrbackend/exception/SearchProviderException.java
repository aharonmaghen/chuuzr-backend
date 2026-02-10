package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class SearchProviderException extends BaseException {
  public SearchProviderException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public SearchProviderException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
