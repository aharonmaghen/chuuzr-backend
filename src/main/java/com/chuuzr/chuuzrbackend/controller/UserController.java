package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.user.UserRequestDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
import com.chuuzr.chuuzrbackend.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userUuid}")
  public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID userUuid) {
    UserResponseDTO user = userService.findByUuid(userUuid);
    if (user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody UserRequestDTO newUserRequest, UriComponentsBuilder ucb) {
    UserResponseDTO createdUser = userService.createUser(newUserRequest);
    URI locationOfNewUser = ucb.path("/api/users/{userUuid}").buildAndExpand(createdUser.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewUser).build();
  }

  @PutMapping("/{userUuid}")
  public ResponseEntity<Void> updateUser(@PathVariable UUID userUuid, @RequestBody UserRequestDTO userToUpdate) {
    UserResponseDTO updatedUser = userService.updateUser(userUuid, userToUpdate);
    if (updatedUser != null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}