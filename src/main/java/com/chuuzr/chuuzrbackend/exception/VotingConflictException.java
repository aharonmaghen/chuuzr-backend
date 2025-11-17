package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class VotingConflictException extends BaseException {
  public VotingConflictException(ErrorCode errorCode) {
    super(errorCode);
  }

  public VotingConflictException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public VotingConflictException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }
}
