package com.chuuzr.chuuzrbackend.service.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.chuuzr.chuuzrbackend.dto.auth.UserAuthResponse;
import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpRequest;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpVerifyRequest;
import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
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
        .orElseGet(() -> createUser(request));

    String otp = generateOtp();
    user.setOtpCode(otp);
    user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(otpExpirationMinutes));
    userRepository.save(user);

    smsService.sendOtp(request.getCountryCode(), request.getPhoneNumber(), otp);
  }

  @Override
  public UserAuthResponse verifyOtp(UserOtpVerifyRequest request) {
    Optional<User> optionalUser = userRepository.findByPhoneNumberAndCountryCode(request.getPhoneNumber(),
        request.getCountryCode());

    if (optionalUser.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    User user = optionalUser.get();

    if (user.getOtpCode() == null || user.getOtpExpirationTime() == null) {
      throw unauthorized("No OTP request found for this user.");
    }

    if (!user.getOtpCode().equals(request.getOtp())) {
      throw unauthorized("Invalid OTP code.");
    }

    if (user.getOtpExpirationTime().isBefore(LocalDateTime.now())) {
      clearOtp(user);
      userRepository.save(user);
      throw unauthorized("OTP code has expired.");
    }

    clearOtp(user);
    userRepository.save(user);

    String jwt = jwtUtil.generateToken(user.getUuid());
    return UserMapper.toAuthResponse(jwt, user.getUuid());
  }

  @Override
  @Transactional(readOnly = true)
  public UserInternalDTO getInternalUserContext(UUID userUuid) {
    return userRepository.findByUuid(userUuid).map(UserMapper::toInternalDTO)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  private User createUser(UserOtpRequest request) {
    User user = new User();
    user.setCountryCode(request.getCountryCode());
    user.setPhoneNumber(request.getPhoneNumber());
    return userRepository.save(user);
  }

  private String generateOtp() {
    int code = otpGenerator.nextInt(900_000) + 100_000;
    return String.format("%06d", code);
  }

  private void clearOtp(User user) {
    user.setOtpCode(null);
    user.setOtpExpirationTime(null);
  }

  private ResponseStatusException unauthorized(String message) {
    return new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
  }
}
