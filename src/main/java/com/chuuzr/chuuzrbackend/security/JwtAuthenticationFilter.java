package com.chuuzr.chuuzrbackend.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.service.auth.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_PREFIX = "Bearer ";

  private final JwtUtil jwtUtil;
  private final AuthService authService;

  public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthService authService) {
    this.jwtUtil = jwtUtil;
    this.authService = authService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

    if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
      String token = authorizationHeader.substring(BEARER_PREFIX.length());
      if (jwtUtil.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
        try {
          UUID userUuid = jwtUtil.extractUserUuid(token);
          UserInternalDTO userContext = authService.getInternalUserContext(userUuid);

          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userContext,
              null, toAuthorities(userContext.getRoles()));
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ResponseStatusException ex) {
          SecurityContextHolder.clearContext();
        }
      }
    }

    filterChain.doFilter(request, response);
  }

  private Set<SimpleGrantedAuthority> toAuthorities(Set<String> roles) {
    if (roles == null || roles.isEmpty()) {
      return Collections.emptySet();
    }
    return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
  }
}
