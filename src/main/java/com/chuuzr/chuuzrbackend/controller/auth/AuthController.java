package com.chuuzr.chuuzrbackend.controller.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chuuzr.chuuzrbackend.dto.auth.UserAuthResponse;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpRequest;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpVerifyRequest;
import com.chuuzr.chuuzrbackend.exception.RefreshTokenException;
import com.chuuzr.chuuzrbackend.service.auth.AuthService;
import com.chuuzr.chuuzrbackend.service.auth.RefreshTokenService;
import com.chuuzr.chuuzrbackend.error.ErrorCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {
  private final AuthService authService;
  private final RefreshTokenService refreshTokenService;
  private final String refreshCookieName;
  private final int refreshCookieMaxAge;
  private final boolean refreshCookieSecure;
  private final String refreshCookieSameSite;
  private final long refreshTokenExpirationDays;

  @Autowired
  public AuthController(AuthService authService, RefreshTokenService refreshTokenService,
      @Value("${jwt.refresh-cookie-name}") String refreshCookieName,
      @Value("#{${jwt.refresh-token-expiration-days} * 24 * 60 * 60}") int refreshCookieMaxAge,
      @Value("${jwt.refresh-cookie-secure}") boolean refreshCookieSecure,
      @Value("${jwt.refresh-cookie-same-site}") String refreshCookieSameSite,
      @Value("${jwt.refresh-token-expiration-days}") long refreshTokenExpirationDays) {
    this.authService = authService;
    this.refreshTokenService = refreshTokenService;
    this.refreshCookieName = refreshCookieName;
    this.refreshCookieMaxAge = refreshCookieMaxAge;
    this.refreshCookieSecure = refreshCookieSecure;
    this.refreshCookieSameSite = refreshCookieSameSite;
    this.refreshTokenExpirationDays = refreshTokenExpirationDays;
  }

  @PostMapping("/otp/request")
  @Operation(summary = "Request OTP for phone authentication", description = "Send an OTP to the specified phone number for authentication", operationId = "requestOtp")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "OTP request accepted", content = @Content),
      @ApiResponse(responseCode = "400", description = "Invalid phone number or country code", content = @Content)
  })
  public ResponseEntity<Void> requestOtp(@Valid @RequestBody UserOtpRequest request) {
    authService.requestOtp(request);
    return ResponseEntity.accepted().build();
  }

  @PostMapping("/otp/verify")
  @Operation(summary = "Verify OTP and authenticate user", description = "Verify the OTP code and return authentication token if valid", operationId = "verifyOtp")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Authentication successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAuthResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid OTP or request data", content = @Content),
      @ApiResponse(responseCode = "401", description = "OTP verification failed", content = @Content)
  })
  public ResponseEntity<UserAuthResponse> verifyOtp(@Valid @RequestBody UserOtpVerifyRequest request,
      HttpServletResponse response) {
    UserAuthResponse authResponse = authService.verifyOtp(request);

    // Generate and set refresh token in cookie only if user exists (not
    // pre-registration)
    if (!authResponse.isRequiresRegistration()) {
      String refreshToken = refreshTokenService.generateAndStoreRefreshToken(
          authResponse.getUserUuid(),
          refreshTokenExpirationDays);

      setRefreshTokenCookie(response, refreshToken);
    }

    return ResponseEntity.ok(authResponse);
  }

  @PostMapping("/refresh")
  @Operation(summary = "Refresh access token", description = "Use refresh token from secure httpOnly cookie to obtain a new access token", operationId = "refreshAccessToken")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Token refreshed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAuthResponse.class))),
      @ApiResponse(responseCode = "400", description = "Missing refresh token in cookie", content = @Content),
      @ApiResponse(responseCode = "401", description = "Refresh token validation failed", content = @Content)
  })
  public ResponseEntity<UserAuthResponse> refreshAccessToken(
      @Parameter(hidden = true) @CookieValue(name = "${jwt.refresh-cookie-name}", required = false) String refreshToken,
      HttpServletResponse response) {

    if (refreshToken == null || refreshToken.isEmpty()) {
      throw new RefreshTokenException(ErrorCode.AUTHENTICATION_FAILED,
          "Refresh token not found in cookie. Please provide a valid refresh token via secure httpOnly cookie.");
    }

    UserAuthResponse authResponse = authService.refreshAccessToken(refreshToken);

    String newRefreshToken = refreshTokenService.generateAndStoreRefreshToken(
        authResponse.getUserUuid(),
        refreshTokenExpirationDays);

    setRefreshTokenCookie(response, newRefreshToken);

    return ResponseEntity.ok(authResponse);
  }

  @PostMapping("/logout")
  @Operation(summary = "Logout user", description = "Invalidate refresh token and clear authentication", operationId = "logout")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Logout successful", content = @Content)
  })
  public ResponseEntity<Void> logout(
      @Parameter(hidden = true) @CookieValue(name = "${jwt.refresh-cookie-name}", required = false) String refreshToken,
      HttpServletResponse response) {

    if (refreshToken != null) {
      refreshTokenService.revokeRefreshToken(refreshToken);
    }

    clearRefreshTokenCookie(response);

    return ResponseEntity.ok().build();
  }

  private void setRefreshTokenCookie(HttpServletResponse response, String token) {
    ResponseCookie cookie = ResponseCookie
        .from(refreshCookieName, token)
        .maxAge(refreshCookieMaxAge)
        .path("/")
        .secure(refreshCookieSecure)
        .httpOnly(true)
        .sameSite(refreshCookieSameSite)
        .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }

  private void clearRefreshTokenCookie(HttpServletResponse response) {
    ResponseCookie cookie = ResponseCookie
        .from(refreshCookieName, "")
        .maxAge(0)
        .path("/")
        .secure(refreshCookieSecure)
        .httpOnly(true)
        .sameSite(refreshCookieSameSite)
        .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }
}
