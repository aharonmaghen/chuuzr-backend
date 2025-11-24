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
import com.chuuzr.chuuzrbackend.dto.room.RoomRequestDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;
import com.chuuzr.chuuzrbackend.service.RoomService;

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
public class RoomController {
  private final RoomService roomService;

  @Autowired
  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping("/{roomUuid}")
  @Operation(summary = "Get room by UUID", description = "Retrieve a specific room by its unique identifier", operationId = "getRoomById")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Room found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Room not found", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  })
  public ResponseEntity<RoomResponseDTO> findById(@PathVariable UUID roomUuid) {
    RoomResponseDTO room = roomService.findByUuid(roomUuid);
    return ResponseEntity.ok(room);
  }

  @PostMapping
  @Operation(summary = "Create a new room", description = "Create a new room with the provided information", operationId = "createRoom")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Room created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "409", description = "Room already exists", content = @Content)
  })
  public ResponseEntity<RoomResponseDTO> createRoom(@Valid @RequestBody RoomRequestDTO newRoomRequest,
      UriComponentsBuilder ucb) {
    RoomResponseDTO createdRoom = roomService.createRoom(newRoomRequest);
    URI locationOfNewRoom = ucb.path("/api/rooms/{roomUuid}").buildAndExpand(createdRoom.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewRoom).body(createdRoom);
  }

  @PutMapping("/{roomUuid}")
  @Operation(summary = "Update an existing room", description = "Update room information for the specified room UUID", operationId = "updateRoom")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Room updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "404", description = "Room not found", content = @Content)
  })
  public ResponseEntity<RoomResponseDTO> updateRoom(@PathVariable UUID roomUuid,
      @Valid @RequestBody RoomRequestDTO roomToUpdate) {
    RoomResponseDTO updatedRoom = roomService.updateRoom(roomUuid, roomToUpdate);
    return ResponseEntity.ok(updatedRoom);
  }
}
