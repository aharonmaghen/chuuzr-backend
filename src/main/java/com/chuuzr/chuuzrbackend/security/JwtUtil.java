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
  private final long expirationMs;
  private SecretKey secretKey;

  public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationMs) {
    this.secret = secret;
    this.expirationMs = expirationMs;
  }

  @PostConstruct
  private void init() {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(UUID userUuid) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(userUuid.toString())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusMillis(expirationMs)))
        .signWith(secretKey)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (RuntimeException ex) {
      return false;
    }
  }

  public UUID extractUserUuid(String token) {
    Claims claims = parseClaims(token).getPayload();
    return UUID.fromString(claims.getSubject());
  }

  private Jws<Claims> parseClaims(String token) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token);
  }
}
