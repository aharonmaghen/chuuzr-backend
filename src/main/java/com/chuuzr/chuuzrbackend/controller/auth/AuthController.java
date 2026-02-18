package com.chuuzr.chuuzrbackend.controller.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.chuuzr.chuuzrbackend.dto.error.ErrorDTO;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpRequest;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpVerifyRequest;
import com.chuuzr.chuuzrbackend.exception.RefreshTokenException;
import com.chuuzr.chuuzrbackend.service.auth.AuthService;
import com.chuuzr.chuuzrbackend.service.auth.RefreshTokenService;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.util.PiiMaskingUtil;

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
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
      @ApiResponse(responseCode = "400", description = "Invalid phone number or country code", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<Void> requestOtp(@Valid @RequestBody UserOtpRequest request) {
    logger.debug("OTP request received for phone: {}",
        PiiMaskingUtil.maskPhoneNumberWithCountry(request.getPhoneNumber(), request.getCountryCode()));

    authService.requestOtp(request);
    logger.info("OTP successfully sent to phone: {}",
        PiiMaskingUtil.maskPhoneNumberWithCountry(request.getPhoneNumber(), request.getCountryCode()));
    return ResponseEntity.accepted().build();
  }

  @PostMapping("/otp/verify")
  @Operation(summary = "Verify OTP and authenticate user", description = "Verify the OTP code and return authentication token if valid", operationId = "verifyOtp")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Authentication successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAuthResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid OTP or request data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "OTP verification failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<UserAuthResponse> verifyOtp(@Valid @RequestBody UserOtpVerifyRequest request,
      HttpServletResponse response) {
    logger.debug("OTP verification request for phone: {}",
        PiiMaskingUtil.maskPhoneNumberWithCountry(request.getPhoneNumber(), request.getCountryCode()));

    UserAuthResponse authResponse = authService.verifyOtp(request);
    logger.info("OTP verified successfully for phone: {}, user: {}, requires registration: {}",
        PiiMaskingUtil.maskPhoneNumberWithCountry(request.getPhoneNumber(), request.getCountryCode()),
        authResponse.getUserUuid(), authResponse.isRequiresRegistration());

    if (!authResponse.isRequiresRegistration()) {
      String refreshToken = refreshTokenService.generateAndStoreRefreshToken(
          authResponse.getUserUuid(),
          refreshTokenExpirationDays);

      logger.debug("Refresh token generated for user: {}", authResponse.getUserUuid());
      setRefreshTokenCookie(response, refreshToken);
    }

    return ResponseEntity.ok(authResponse);
  }

  @PostMapping("/refresh")
  @Operation(summary = "Refresh access token", description = "Use refresh token from secure httpOnly cookie to obtain a new access token", operationId = "refreshAccessToken")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Token refreshed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAuthResponse.class))),
      @ApiResponse(responseCode = "400", description = "Missing refresh token in cookie", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Refresh token validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<UserAuthResponse> refreshAccessToken(
      @Parameter(hidden = true) @CookieValue(name = "${jwt.refresh-cookie-name}", required = false) String refreshToken,
      HttpServletResponse response) {

    if (refreshToken == null || refreshToken.isEmpty()) {
      logger.warn("Refresh token refresh attempt without valid token cookie");
      throw new RefreshTokenException(ErrorCode.AUTHENTICATION_FAILED,
          "Refresh token not found in cookie. Please provide a valid refresh token via secure httpOnly cookie.");
    }

    logger.debug("Token refresh request received, token: {}", PiiMaskingUtil.maskToken(refreshToken));

    UserAuthResponse authResponse = authService.refreshAccessToken(refreshToken);
    logger.info("Access token refreshed for user: {}", authResponse.getUserUuid());

    String newRefreshToken = refreshTokenService.generateAndStoreRefreshToken(
        authResponse.getUserUuid(),
        refreshTokenExpirationDays);

    logger.debug("New refresh token generated for user: {}", authResponse.getUserUuid());
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

    logger.debug("Logout request received");

    if (refreshToken != null && !refreshToken.isEmpty()) {
      refreshTokenService.revokeRefreshToken(refreshToken);
      logger.info("Refresh token revoked and user logged out");
    } else {
      logger.debug("Logout request without valid refresh token cookie");
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
