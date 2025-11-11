package com.chuuzr.chuuzrbackend.service.auth;

import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.auth.UserAuthResponse;
import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpRequest;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpVerifyRequest;

public interface AuthService {
  void requestOtp(UserOtpRequest request);

  UserAuthResponse verifyOtp(UserOtpVerifyRequest request);

  UserInternalDTO getInternalUserContext(UUID userUuid);
}
