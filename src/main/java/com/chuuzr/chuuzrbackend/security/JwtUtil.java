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

@Component
public class JwtUtil {
  private final String secret;
  private final Long expirationMs;
  private final Long registrationExpirationMs;
  private SecretKey secretKey;

  public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expirationMs,
      @Value("${jwt.registrationExpiration}") Long registrationExpirationMs) {
    this.secret = secret;
    this.expirationMs = expirationMs;
    this.registrationExpirationMs = registrationExpirationMs;
  }

  @PostConstruct
  private void init() {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(UUID userUuid) {
    return buildToken(userUuid.toString(), expirationMs, "ROLE_USER");
  }

  public String generateRegistrationToken(String phoneNumber) {
    return buildToken(phoneNumber, registrationExpirationMs, "ROLE_PRE_REGISTER");
  }

  private String buildToken(String subject, long duration, String role) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .claim("role", role)
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
}
