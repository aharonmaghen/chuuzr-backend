package com.chuuzr.chuuzrbackend.service.auth;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.chuuzr.chuuzrbackend.util.RedisKeyConstants;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
  private static final Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);
  private final StringRedisTemplate stringRedisTemplate;

  public RefreshTokenServiceImpl(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Override
  public String generateAndStoreRefreshToken(UUID userUuid, long expirationDays) {
    String opaqueToken = UUID.randomUUID().toString();
    String key = RedisKeyConstants.REFRESH_TOKEN_PREFIX + opaqueToken;

    logger.debug("Generating refresh token for user: {} with {} day expiration", userUuid, expirationDays);
    stringRedisTemplate.opsForValue().set(
        key,
        userUuid.toString(),
        expirationDays,
        TimeUnit.DAYS);

    return opaqueToken;
  }

  @Override
  public Optional<UUID> validateRefreshToken(String refreshToken) {
    String key = RedisKeyConstants.REFRESH_TOKEN_PREFIX + refreshToken;
    String userUuidStr = stringRedisTemplate.opsForValue().get(key);

    if (userUuidStr == null) {
      logger.debug("Refresh token validation failed - token not found or expired");
      return Optional.empty();
    }

    logger.debug("Refresh token valid for user: {}", userUuidStr);
    return Optional.of(UUID.fromString(userUuidStr));
  }

  @Override
  public void revokeRefreshToken(String refreshToken) {
    String key = RedisKeyConstants.REFRESH_TOKEN_PREFIX + refreshToken;
    stringRedisTemplate.delete(key);
  }

  @Override
  public void revokeAllUserTokens(UUID userUuid) {
    // Implementation: would need to scan all refresh tokens and delete those
    // belonging to the user. For now, individual revocation is supported.
    // This can be enhanced in the future if needed.
  }
}
