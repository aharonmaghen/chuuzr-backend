package com.chuuzr.chuuzrbackend.service.auth;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
  private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
  private final StringRedisTemplate stringRedisTemplate;

  public RefreshTokenServiceImpl(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Override
  public String generateAndStoreRefreshToken(UUID userUuid, long expirationDays) {
    String opaqueToken = UUID.randomUUID().toString();
    String key = REFRESH_TOKEN_PREFIX + opaqueToken;

    stringRedisTemplate.opsForValue().set(
        key,
        userUuid.toString(),
        expirationDays,
        TimeUnit.DAYS);

    return opaqueToken;
  }

  @Override
  public Optional<UUID> validateRefreshToken(String refreshToken) {
    String key = REFRESH_TOKEN_PREFIX + refreshToken;
    String userUuidStr = stringRedisTemplate.opsForValue().get(key);

    if (userUuidStr == null) {
      return Optional.empty();
    }

    return Optional.of(UUID.fromString(userUuidStr));
  }

  @Override
  public void revokeRefreshToken(String refreshToken) {
    String key = REFRESH_TOKEN_PREFIX + refreshToken;
    stringRedisTemplate.delete(key);
  }

  @Override
  public void revokeAllUserTokens(UUID userUuid) {
    // Implementation: would need to scan all refresh tokens and delete those
    // belonging to the user. For now, individual revocation is supported.
    // This can be enhanced in the future if needed.
  }
}
