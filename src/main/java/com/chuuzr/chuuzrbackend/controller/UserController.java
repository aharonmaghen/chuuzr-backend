package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.UUID;

import jakarta.validation.Valid;

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
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(summary = "Get user by UUID", description = "Retrieve a specific user by their unique identifier", operationId = "getUserById")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  })
  public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID userUuid) {
    UserResponseDTO user = userService.findByUuid(userUuid);
    return ResponseEntity.ok(user);
  }

  @PostMapping
  @Operation(summary = "Create a new user", description = "Create a new user with the provided information. All fields are required except profile picture.", operationId = "createUser")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
  })
  public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO newUserRequest, UriComponentsBuilder ucb) {
    UserResponseDTO createdUser = userService.createUser(newUserRequest);
    URI locationOfNewUser = ucb.path("/api/users/{userUuid}").buildAndExpand(createdUser.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewUser).body(createdUser);
  }

  @PutMapping("/{userUuid}")
  @Operation(summary = "Update an existing user", description = "Update user information for the specified user UUID. All fields are required except profile picture.", operationId = "updateUser")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
  })
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID userUuid, @Valid @RequestBody UserRequestDTO userToUpdate) {
    UserResponseDTO updatedUser = userService.updateUser(userUuid, userToUpdate);
    return ResponseEntity.ok(updatedUser);
  }
}