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
import com.chuuzr.chuuzrbackend.dto.room.RoomRequestDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;
import com.chuuzr.chuuzrbackend.service.RoomService;

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
  public ResponseEntity<RoomResponseDTO> findById(@PathVariable UUID roomUuid) {
    RoomResponseDTO room = roomService.findByUuid(roomUuid);
    if (room != null) {
      return ResponseEntity.ok(room);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody RoomRequestDTO newRoomRequest,
      UriComponentsBuilder ucb) {
    RoomResponseDTO createdRoom = roomService.createRoom(newRoomRequest);
    URI locationOfNewRoom = ucb.path("/api/rooms/{roomUuid}").buildAndExpand(createdRoom.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewRoom).body(createdRoom);
  }

  @PutMapping("/{roomUuid}")
  public ResponseEntity<RoomResponseDTO> updateRoom(@PathVariable UUID roomUuid,
      @RequestBody RoomRequestDTO roomToUpdate) {
    RoomResponseDTO updatedRoom = roomService.updateRoom(roomUuid, roomToUpdate);
    if (updatedRoom != null) {
      return ResponseEntity.ok(updatedRoom);
    }
    return ResponseEntity.notFound().build();
  }
}
