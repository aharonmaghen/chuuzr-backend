package com.chuuzr.chuuzrbackend.service.auth;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
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
import com.chuuzr.chuuzrbackend.util.CountryCodeUtil;
import com.chuuzr.chuuzrbackend.util.ValidationUtil;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final StringRedisTemplate stringRedisTemplate;
  private final SmsService smsService;
  private final JwtUtil jwtUtil;
  private final Long otpExpirationMinutes;
  private final SecureRandom otpGenerator = new SecureRandom();

  public AuthServiceImpl(UserRepository userRepository, StringRedisTemplate stringRedisTemplate, SmsService smsService,
      JwtUtil jwtUtil,
      @Value("${otp.expiration.minutes}") Long otpExpirationMinutes) {
    this.userRepository = userRepository;
    this.stringRedisTemplate = stringRedisTemplate;
    this.smsService = smsService;
    this.jwtUtil = jwtUtil;
    this.otpExpirationMinutes = otpExpirationMinutes;
  }

  @Override
  public void requestOtp(UserOtpRequest request) {
    String normalizedPhone = ValidationUtil.normalizePhoneNumber(request.getPhoneNumber(), request.getCountryCode());
    if (normalizedPhone == null) {
      throw new IllegalArgumentException("Invalid phone number for country code: " + request.getCountryCode());
    }

    String fullPhoneNumber = CountryCodeUtil.toDialCode(request.getCountryCode()) + normalizedPhone;
    String otp = generateOtp();

    stringRedisTemplate.opsForValue().set(
        "otp:" + fullPhoneNumber,
        otp,
        otpExpirationMinutes,
        TimeUnit.MINUTES);

    smsService.sendOtp(request.getCountryCode(), normalizedPhone, otp);
  }

  @Override
  public UserAuthResponse verifyOtp(UserOtpVerifyRequest request) {
    String normalizedPhone = ValidationUtil.normalizePhoneNumber(request.getPhoneNumber(), request.getCountryCode());
    if (normalizedPhone == null) {
      throw new AuthorizationException(ErrorCode.OTP_INVALID,
          "Invalid phone number for country code: " + request.getCountryCode());
    }

    String fullPhone = CountryCodeUtil.toDialCode(request.getCountryCode()) + normalizedPhone;
    String redisKey = "otp:" + fullPhone;

    String storedOtp = stringRedisTemplate.opsForValue().get(redisKey);

    if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
      throw new AuthorizationException(ErrorCode.OTP_INVALID, "Invalid or expired OTP");
    }

    stringRedisTemplate.delete(redisKey);

    return userRepository.findByPhoneNumberAndCountryCode(normalizedPhone, request.getCountryCode())
        .map(user -> {
          String jwt = jwtUtil.generateToken(user.getUuid());
          return UserMapper.toAuthResponse(jwt, user.getUuid(), false);
        })
        .orElseGet(() -> {
          String tempJwt = jwtUtil.generateRegistrationToken(fullPhone);
          return UserMapper.toAuthResponse(tempJwt, null, true);
        });
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
}
