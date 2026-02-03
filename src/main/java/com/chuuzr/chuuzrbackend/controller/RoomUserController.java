package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserRequestDTO;
import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserResponseDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
import com.chuuzr.chuuzrbackend.service.RoomUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Rooms")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class RoomUserController {
  private static final Logger logger = LoggerFactory.getLogger(RoomUserController.class);

  private final RoomUserService roomUserService;

  @Autowired
  public RoomUserController(RoomUserService roomUserService) {
    this.roomUserService = roomUserService;
  }

  @GetMapping("/{roomUuid}/users")
  @Operation(summary = "Get users in a room", description = "Retrieve all users that are members of a specific room with pagination support", operationId = "getRoomUsers")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  })
  public ResponseEntity<List<UserResponseDTO>> getRoomUsers(@PathVariable UUID roomUuid,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    logger.debug("Get room users request for roomUuid={}", roomUuid);
    List<UserResponseDTO> users = roomUserService.getRoomUsers(roomUuid, pageable);
    logger.info("Room users retrieved for roomUuid={}, count={}", roomUuid, users.size());
    return ResponseEntity.ok(users);
  }

  @PostMapping("/{roomUuid}/users")
  @Operation(summary = "Add user to room", description = "Add a user to a specific room", operationId = "addUserToRoom")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User added to room successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomUserResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "404", description = "Room or user not found", content = @Content),
      @ApiResponse(responseCode = "409", description = "User already in room", content = @Content)
  })
  public ResponseEntity<RoomUserResponseDTO> addUserToRoom(@PathVariable UUID roomUuid,
      @Valid @RequestBody RoomUserRequestDTO roomUserRequest,
      UriComponentsBuilder ucb) {
    logger.debug("Add user to room request for roomUuid={}, userUuid={}", roomUuid,
        roomUserRequest.getUserUuid());
    RoomUserResponseDTO addedRoomUser = roomUserService.addUserToRoom(roomUuid, roomUserRequest.getUserUuid());
    URI locationOfNewUser = ucb.path("/api/rooms/{roomUuid}/users")
        .buildAndExpand(addedRoomUser.getRoom().getUuid()).toUri();
    logger.info("User added to room for roomUuid={}, userUuid={}", roomUuid,
        roomUserRequest.getUserUuid());
    return ResponseEntity.created(locationOfNewUser).body(addedRoomUser);
  }
}