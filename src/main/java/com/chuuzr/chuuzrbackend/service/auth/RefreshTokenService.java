package com.chuuzr.chuuzrbackend.service.auth;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
  String generateAndStoreRefreshToken(UUID userUuid, long expirationDays);

  Optional<UUID> validateRefreshToken(String refreshToken);

  void revokeRefreshToken(String refreshToken);

  void revokeAllUserTokens(UUID userUuid);
}
