package com.chuuzr.chuuzrbackend.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;

import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.AuthorizationException;

@Component
public class JwtUtil {
  private final String secret;
  private final long registrationExpirationMs;
  private final long accessTokenExpirationMs;
  private SecretKey secretKey;

  public JwtUtil(@Value("${jwt.secret}") String secret,
      @Value("${jwt.registration-expiration-ms}") long registrationExpirationMs,
      @Value("${jwt.access-token-expiration-ms}") long accessTokenExpirationMs) {
    this.secret = secret;
    this.registrationExpirationMs = registrationExpirationMs;
    this.accessTokenExpirationMs = accessTokenExpirationMs;
  }

  @PostConstruct
  private void init() {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(UUID userUuid) {
    return buildToken(userUuid.toString(), accessTokenExpirationMs, "ROLE_USER", "ACCESS");
  }

  public String generateAccessToken(String subject, String role) {
    return buildToken(subject, accessTokenExpirationMs, role, "ACCESS");
  }

  public String generateRegistrationToken(String phoneNumber) {
    return buildToken(phoneNumber, registrationExpirationMs, "ROLE_PRE_REGISTER", "ACCESS");
  }

  private String buildToken(String subject, long duration, String role, String tokenType) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .claim("role", role)
        .claim("tokenType", tokenType)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusMillis(duration)))
        .signWith(secretKey)
        .compact();
  }

  public String extractRole(String token) {
    return parseClaims(token).getPayload().get("role", String.class);
  }

  public String extractSubject(String token) {
    return parseClaims(token).getPayload().getSubject();
  }

  public UUID extractUserUuid(String token) {
    Claims claims = parseClaims(token).getPayload();
    String role = (String) claims.get("role");
    if (!"ROLE_USER".equals(role)) {
      throw new IllegalStateException("Not a standard user token. Current role: " + role);
    }
    return UUID.fromString(claims.getSubject());
  }

  public String extractPhoneNumberFromRegistrationToken(String token) {
    Claims claims = parseClaims(token).getPayload();
    String role = (String) claims.get("role");
    if (!"ROLE_PRE_REGISTER".equals(role)) {
      throw new IllegalStateException("Not a registration token. Current role: " + role);
    }
    return claims.getSubject();
  }

  private Jws<Claims> parseClaims(String token) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token);
  }

  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String extractTokenType(String token) {
    try {
      return parseClaims(token).getPayload().get("tokenType", String.class);
    } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
      throw new AuthorizationException(ErrorCode.JWT_INVALID, "Failed to extract token type from JWT");
    }
  }

  public boolean validateAccessToken(String token) {
    try {
      String tokenType = extractTokenType(token);
      if (!"ACCESS".equals(tokenType)) {
        return false;
      }
      return validateToken(token);
    } catch (Exception e) {
      return false;
    }
  }
}
