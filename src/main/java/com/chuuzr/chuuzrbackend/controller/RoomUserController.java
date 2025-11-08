package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserRequestDTO;
import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserResponseDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
import com.chuuzr.chuuzrbackend.service.RoomUserService;

@RestController
@RequestMapping("/api/room-users")
public class RoomUserController {
  private final RoomUserService roomUserService;

  @Autowired
  public RoomUserController(RoomUserService roomUserService) {
    this.roomUserService = roomUserService;
  }

  @GetMapping("/{roomUuid}/users")
  public ResponseEntity<List<UserResponseDTO>> getRoomUsers(@PathVariable UUID roomUuid,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    List<UserResponseDTO> users = roomUserService.getRoomUsers(roomUuid, pageable);
    return ResponseEntity.ok(users);
  }

  @PostMapping("/{roomUuid}/add-user")
  public ResponseEntity<RoomUserResponseDTO> addUserToRoom(@PathVariable UUID roomUuid,
      @RequestBody RoomUserRequestDTO roomUserRequest,
      UriComponentsBuilder ucb) {
    RoomUserResponseDTO addedRoomUser = roomUserService.addUserToRoom(roomUuid, roomUserRequest.getUserUuid());
    if (addedRoomUser != null) {
      URI locationOfNewUser = ucb.path("/api/room-users/{roomUuid}/users")
          .buildAndExpand(addedRoomUser.getRoom().getUuid())
          .toUri();
      return ResponseEntity.created(locationOfNewUser).body(addedRoomUser);
    }
    return ResponseEntity.notFound().build();
  }
}