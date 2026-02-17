package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.UUID;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserCreateRequestDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserCreatedResponseDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserRequestDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
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
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  @Operation(summary = "Get current user profile", description = "Retrieve the authenticated user's profile information", operationId = "getCurrentUserProfile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User profile retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized - authentication required or invalid token", content = @Content)
  })
  public ResponseEntity<UserResponseDTO> getCurrentUser(Authentication authentication) {
    UserInternalDTO userContext = (UserInternalDTO) authentication.getPrincipal();
    logger.debug("Get current user profile request for userUuid={}", userContext.getUuid());
    UserResponseDTO user = userService.findByUuid(userContext.getUuid());
    logger.info("User profile retrieved for userUuid={}", userContext.getUuid());
    return ResponseEntity.ok(user);
  }

  @PutMapping("/me")
  @Operation(summary = "Update current user profile", description = "Update the authenticated user's profile information. All fields are required except profile picture.", operationId = "updateCurrentUserProfile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User profile updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized - authentication required or invalid token", content = @Content)
  })
  public ResponseEntity<UserResponseDTO> updateCurrentUser(Authentication authentication,
      @Valid @RequestBody UserRequestDTO userToUpdate) {
    UserInternalDTO userContext = (UserInternalDTO) authentication.getPrincipal();
    logger.debug("Update current user profile request for userUuid={}", userContext.getUuid());
    UserResponseDTO updatedUser = userService.updateUser(userContext.getUuid(), userToUpdate);
    logger.info("User profile updated for userUuid={}", userContext.getUuid());
    return ResponseEntity.ok(updatedUser);
  }

  @GetMapping("/{userUuid}")
  @Operation(summary = "Get user by UUID", description = "Retrieve a specific user by their unique identifier", operationId = "getUserById")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  })
  public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID userUuid) {
    logger.debug("Get user by UUID request for userUuid={}", userUuid);
    UserResponseDTO user = userService.findByUuid(userUuid);
    logger.info("User retrieved for userUuid={}", userUuid);
    return ResponseEntity.ok(user);
  }

  @PostMapping
  @Operation(summary = "Create a new user", description = "Create a new user with the provided information. All fields are required except profile picture. Returns authentication token for newly created user.", operationId = "createUser")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created successfully with authentication token", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserCreatedResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
  })
  public ResponseEntity<UserCreatedResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO newUserRequest,
      UriComponentsBuilder ucb) {
    logger.debug("Create user request received");
    UserCreatedResponseDTO createdUser = userService.createUser(newUserRequest);
    URI locationOfNewUser = ucb.path("/api/users/{userUuid}").buildAndExpand(createdUser.getUser().getUuid()).toUri();
    logger.info("User created with userUuid={}", createdUser.getUser().getUuid());
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
  public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID userUuid,
      @Valid @RequestBody UserRequestDTO userToUpdate) {
    logger.debug("Update user request for userUuid={}", userUuid);
    UserResponseDTO updatedUser = userService.updateUser(userUuid, userToUpdate);
    logger.info("User updated for userUuid={}", userUuid);
    return ResponseEntity.ok(updatedUser);
  }
}