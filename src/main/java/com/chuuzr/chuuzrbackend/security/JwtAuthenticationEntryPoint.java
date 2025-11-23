package com.chuuzr.chuuzrbackend.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.chuuzr.chuuzrbackend.dto.error.ErrorDTO;
import com.chuuzr.chuuzrbackend.dto.error.ErrorMapper;
import com.chuuzr.chuuzrbackend.error.ErrorCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final ObjectMapper objectMapper;

  public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    ErrorCode errorCode = ErrorCode.AUTHENTICATION_FAILED;

    Throwable cause = authException != null ? authException.getCause() : null;
    if (cause instanceof io.jsonwebtoken.JwtException) {
      errorCode = ErrorCode.JWT_INVALID;
    }

    response.setStatus(errorCode.getStatus().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    ErrorDTO error = ErrorMapper.toErrorDTO(errorCode, null, request.getRequestURI());

    objectMapper.writeValue(response.getWriter(), error);
  }
}
