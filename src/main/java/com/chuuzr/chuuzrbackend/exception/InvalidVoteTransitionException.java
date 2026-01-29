package com.chuuzr.chuuzrbackend.exception;

import com.chuuzr.chuuzrbackend.error.ErrorCode;

public class InvalidVoteTransitionException extends BaseException {

  public InvalidVoteTransitionException() {
    super(ErrorCode.INVALID_VOTE_TRANSITION);
  }

  public InvalidVoteTransitionException(String message) {
    super(ErrorCode.INVALID_VOTE_TRANSITION, message);
  }

  public InvalidVoteTransitionException(String message, Throwable cause) {
    super(ErrorCode.INVALID_VOTE_TRANSITION, message, cause);
  }
}
