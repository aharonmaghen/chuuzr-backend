package com.chuuzr.chuuzrbackend.controller.auth;

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
  public ResponseEntity<Void> requestOtp(@RequestBody UserOtpRequest request) {
    authService.requestOtp(request);
    return ResponseEntity.accepted().build();
  }

  @PostMapping("/otp/verify")
  public ResponseEntity<UserAuthResponse> verifyOtp(@RequestBody UserOtpVerifyRequest request) {
    UserAuthResponse response = authService.verifyOtp(request);
    return ResponseEntity.ok(response);
  }
}
