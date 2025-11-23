package com.chuuzr.chuuzrbackend.service.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.auth.UserAuthResponse;
import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpRequest;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpVerifyRequest;
import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.AuthorizationException;
import com.chuuzr.chuuzrbackend.exception.UserNotFoundException;
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.repository.UserRepository;
import com.chuuzr.chuuzrbackend.security.JwtUtil;
import com.chuuzr.chuuzrbackend.service.sms.SmsService;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final SmsService smsService;
  private final JwtUtil jwtUtil;
  private final long otpExpirationMinutes;
  private final SecureRandom otpGenerator = new SecureRandom();

  public AuthServiceImpl(UserRepository userRepository, SmsService smsService, JwtUtil jwtUtil,
      @Value("${otp.expiration.minutes}") long otpExpirationMinutes) {
    this.userRepository = userRepository;
    this.smsService = smsService;
    this.jwtUtil = jwtUtil;
    this.otpExpirationMinutes = otpExpirationMinutes;
  }

  @Override
  public void requestOtp(UserOtpRequest request) {
    User user = userRepository.findByPhoneNumberAndCountryCode(request.getPhoneNumber(), request.getCountryCode())
        .orElseThrow(() -> new UserNotFoundException("User with phone number " + request.getPhoneNumber()
            + " and country code " + request.getCountryCode() + " not found"));

    String otp = generateOtp();
    user.setOtpCode(otp);
    user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(otpExpirationMinutes));
    userRepository.save(user);

    smsService.sendOtp(request.getCountryCode(), request.getPhoneNumber(), otp);
  }

  @Override
  public UserAuthResponse verifyOtp(UserOtpVerifyRequest request) {
    User user = userRepository.findByPhoneNumberAndCountryCode(request.getPhoneNumber(), request.getCountryCode())
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    if (user.getOtpCode() == null || user.getOtpExpirationTime() == null) {
      throw new AuthorizationException(ErrorCode.OTP_INVALID, "Invalid OTP code");
    }

    if (user.getOtpExpirationTime().isBefore(LocalDateTime.now())) {
      clearOtp(user);
      userRepository.save(user);
      throw new AuthorizationException(ErrorCode.OTP_INVALID, "Invalid OTP code");
    }

    if (!user.getOtpCode().equals(request.getOtp())) {
      throw new AuthorizationException(ErrorCode.OTP_INVALID, "Invalid OTP code");
    }

    clearOtp(user);
    userRepository.save(user);

    String jwt = jwtUtil.generateToken(user.getUuid());
    return UserMapper.toAuthResponse(jwt, user.getUuid());
  }

  @Override
  @Transactional(readOnly = true)
  public UserInternalDTO getInternalUserContext(UUID userUuid) {
    User user = userRepository.findByUuid(userUuid).orElseThrow(
        () -> new UserNotFoundException("User with UUID " + userUuid + " not found"));
    return UserMapper.toInternalDTO(user);
  }

  private String generateOtp() {
    int code = otpGenerator.nextInt(900_000) + 100_000;
    return String.format("%06d", code);
  }

  private void clearOtp(User user) {
    user.setOtpCode(null);
    user.setOtpExpirationTime(null);
  }
}
