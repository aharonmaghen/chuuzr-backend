package com.movienight.movienightbackend.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.movienight.movienightbackend.models.RoomUser;
import com.movienight.movienightbackend.models.User;
import com.movienight.movienightbackend.models.Room;
import com.movienight.movienightbackend.repositories.RoomRepository;
import com.movienight.movienightbackend.repositories.RoomUserRepository;
import com.movienight.movienightbackend.repositories.UserRepository;

@RestController
@RequestMapping("/api/room-users")
public class RoomUserController {
  private RoomUserRepository roomUserRepository;
  private RoomRepository roomRepository;
  private UserRepository userRepository;

  @Autowired
  public RoomUserController(RoomUserRepository roomUserRepository, RoomRepository roomRepository,
      UserRepository userRepository) {
    this.roomUserRepository = roomUserRepository;
    this.roomRepository = roomRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("/{roomUuid}/users")
  public ResponseEntity<List<User>> getRoomUsers(@PathVariable UUID roomUuid, Pageable pageable) {
    Page<User> page = roomUserRepository.findByRoomUuid(roomUuid, PageRequest.of(pageable.getPageNumber(),
        pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "userId")))).map(RoomUser::getUser);
    return ResponseEntity.ok(page.getContent());
  }

  @PostMapping("/{roomUuid}/add-user")
  public ResponseEntity<Void> addUserToRoom(@PathVariable UUID roomUuid, @RequestBody Map<String, Object> roomUser,
      UriComponentsBuilder ucb) {
    Room room = roomRepository.findByUuid(roomUuid).orElse(null);
    User user = userRepository.findByUuid(UUID.fromString(roomUser.get("userUuid").toString())).orElse(null);
    if (room != null && user != null) {
      RoomUser roomUserToAdd = new RoomUser(null, room, user, LocalDateTime.now(), LocalDateTime.now());
      RoomUser addedRoomUser = roomUserRepository.save(roomUserToAdd);
      URI locationOfNewUser = ucb.path("/api/room-users/{roomUuid}/users").buildAndExpand(addedRoomUser.getRoom().getUuid())
          .toUri();
      return ResponseEntity.created(locationOfNewUser).build();
    }
    return ResponseEntity.notFound().build();
  }
}