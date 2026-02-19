package com.chuuzr.chuuzrbackend.service.auth;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.chuuzr.chuuzrbackend.util.RedisKeyConstants;
import com.chuuzr.chuuzrbackend.util.PiiMaskingUtil;
import com.chuuzr.chuuzrbackend.util.ValidationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

  private final UserRepository userRepository;
  private final StringRedisTemplate stringRedisTemplate;
  private final SmsService smsService;
  private final JwtUtil jwtUtil;
  private final RefreshTokenService refreshTokenService;
  private final ObjectMapper objectMapper;
  private final long otpExpirationMinutes;
  private final long registrationExpirationMs;
  private final String fixedOtpValue;
  private final SecureRandom otpGenerator = new SecureRandom();

  public AuthServiceImpl(UserRepository userRepository, StringRedisTemplate stringRedisTemplate, SmsService smsService,
      JwtUtil jwtUtil, RefreshTokenService refreshTokenService, ObjectMapper objectMapper,
      @Value("${otp.expiration.minutes}") long otpExpirationMinutes,
      @Value("${jwt.registration-expiration-ms}") long registrationExpirationMs,
      @Value("${otp.fixed-value:}") String fixedOtpValue) {
    this.userRepository = userRepository;
    this.stringRedisTemplate = stringRedisTemplate;
    this.smsService = smsService;
    this.jwtUtil = jwtUtil;
    this.refreshTokenService = refreshTokenService;
    this.objectMapper = objectMapper;
    this.otpExpirationMinutes = otpExpirationMinutes;
    this.registrationExpirationMs = registrationExpirationMs;
    this.fixedOtpValue = fixedOtpValue;
  }

  @Override
  public void requestOtp(UserOtpRequest request) {
    String normalizedCountryCode = request.getCountryCode().trim().toUpperCase();

    logger.debug("OTP request received for phone: {}",
        PiiMaskingUtil.maskPhoneNumberWithCountry(request.getPhoneNumber(), normalizedCountryCode));

    String normalizedPhone = ValidationUtil.normalizePhoneNumber(request.getPhoneNumber(), normalizedCountryCode);
    if (normalizedPhone == null) {
      throw new IllegalArgumentException("Invalid phone number for country code: " + normalizedCountryCode);
    }

    String fullPhoneNumber = CountryCodeUtil.toDialCode(normalizedCountryCode) + normalizedPhone;
    String otp = generateOtp();

    logger.debug("Storing OTP in Redis with expiration of {} minutes", otpExpirationMinutes);
    stringRedisTemplate.opsForValue().set(
        RedisKeyConstants.OTP_PREFIX + fullPhoneNumber,
        otp,
        otpExpirationMinutes,
        TimeUnit.MINUTES);

    smsService.sendOtp(normalizedCountryCode, normalizedPhone, otp);
  }

  @Override
  public UserAuthResponse verifyOtp(UserOtpVerifyRequest request) {
    String normalizedCountryCode = request.getCountryCode().trim().toUpperCase();

    logger.debug("OTP verification request for phone: {}",
        PiiMaskingUtil.maskPhoneNumberWithCountry(request.getPhoneNumber(), normalizedCountryCode));

    String normalizedPhone = ValidationUtil.normalizePhoneNumber(request.getPhoneNumber(), normalizedCountryCode);
    if (normalizedPhone == null) {
      throw new AuthorizationException(ErrorCode.OTP_INVALID,
          "Invalid phone number for country code: " + normalizedCountryCode);
    }

    String fullPhone = CountryCodeUtil.toDialCode(normalizedCountryCode) + normalizedPhone;
    String redisKey = RedisKeyConstants.OTP_PREFIX + fullPhone;

    String storedOtp = stringRedisTemplate.opsForValue().get(redisKey);
    logger.debug("Comparing OTP - provided: {}, stored: {}",
        PiiMaskingUtil.maskOtp(request.getOtp()),
        storedOtp != null ? PiiMaskingUtil.maskOtp(storedOtp) : "null");

    if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
      logger.warn("OTP verification failed for phone: {} - provided: {}",
          PiiMaskingUtil.maskPhoneNumberWithCountry(request.getPhoneNumber(), normalizedCountryCode),
          PiiMaskingUtil.maskOtp(request.getOtp()));
      throw new AuthorizationException(ErrorCode.OTP_INVALID, "Invalid or expired OTP");
    }

    stringRedisTemplate.delete(redisKey);
    logger.debug("OTP verified and deleted from Redis");

    return userRepository.findByPhoneNumberAndCountryCode(normalizedPhone, normalizedCountryCode)
        .map(user -> {
          String jwt = jwtUtil.generateToken(user.getUuid());
          logger.debug("Access token generated for existing user: {}", user.getUuid());
          return UserMapper.toAuthResponse(jwt, user.getUuid(), false);
        })
        .orElseGet(() -> {
          String preRegUuid = UUID.randomUUID().toString();
          String redisValue;
          try {
            redisValue = objectMapper.writeValueAsString(
                Map.of("countryCode", normalizedCountryCode, "phoneNumber", normalizedPhone));
          } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize pre-registration data", e);
          }
          stringRedisTemplate.opsForValue().set(
              RedisKeyConstants.PRE_REG_PREFIX + preRegUuid,
              redisValue,
              registrationExpirationMs,
              TimeUnit.MILLISECONDS);
          String tempJwt = jwtUtil.generateRegistrationToken(preRegUuid);
          logger.debug("Registration token generated for new user with pre-reg key: {}", preRegUuid);
          return UserMapper.toAuthResponse(tempJwt, null, true);
        });
  }

  @Override
  @Transactional(readOnly = true)
  public UserInternalDTO getInternalUserContext(UUID userUuid) {
    logger.debug("Loading user context for userUuid: {}", userUuid);
    User user = userRepository.findByUuid(userUuid).orElseThrow(
        () -> new UserNotFoundException("User with UUID " + userUuid + " not found"));
    logger.debug("User context loaded successfully");
    return UserMapper.toInternalDTO(user);
  }

  @Override
  public UserAuthResponse refreshAccessToken(String refreshToken) {
    Optional<UUID> userUuid = refreshTokenService.validateRefreshToken(refreshToken);

    if (userUuid.isEmpty()) {
      logger.warn("Refresh token validation failed - token invalid or expired");
      throw new AuthorizationException(ErrorCode.REFRESH_TOKEN_INVALID, "Invalid or expired refresh token");
    }

    refreshTokenService.revokeRefreshToken(refreshToken);
    logger.debug("Old refresh token revoked");

    String newAccessToken = jwtUtil.generateAccessToken(userUuid.get().toString(), "ROLE_USER");
    logger.debug("New access token generated for user: {}", userUuid.get());

    return UserMapper.toAuthResponse(newAccessToken, userUuid.get(), false);
  }

  private String generateOtp() {
    if (!fixedOtpValue.isBlank()) {
      return fixedOtpValue;
    }
    int code = otpGenerator.nextInt(900_000) + 100_000;
    return String.format("%06d", code);
  }
}
