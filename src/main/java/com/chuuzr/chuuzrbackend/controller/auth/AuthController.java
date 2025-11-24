package com.chuuzr.chuuzrbackend.controller.auth;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chuuzr.chuuzrbackend.dto.auth.UserAuthResponse;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpRequest;
import com.chuuzr.chuuzrbackend.dto.auth.UserOtpVerifyRequest;
import com.chuuzr.chuuzrbackend.service.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
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
  public ResponseEntity<UserAuthResponse> verifyOtp(@Valid @RequestBody UserOtpVerifyRequest request) {
    UserAuthResponse response = authService.verifyOtp(request);
    return ResponseEntity.ok(response);
  }
}
