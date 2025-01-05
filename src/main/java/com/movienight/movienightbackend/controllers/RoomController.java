package com.movienight.movienightbackend.controllers;

import java.net.URI;
import java.time.LocalDateTime;
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

import com.movienight.movienightbackend.models.Room;
import com.movienight.movienightbackend.repositories.RoomRepository;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
  private RoomRepository roomRepository;

  @Autowired
  public RoomController(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @GetMapping("/{roomUuid}")
  public ResponseEntity<Room> findById(@PathVariable UUID roomUuid) {
    Room room = findRoom(roomUuid);
    if (room != null) {
      return ResponseEntity.ok(room);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Void> createRoom(@RequestBody Room newRoomRequest, UriComponentsBuilder ucb) {
    Room roomToSave = new Room(null, null, newRoomRequest.getName(), LocalDateTime.now(), LocalDateTime.now());
    Room savedRoom = roomRepository.save(roomToSave);
    URI locationOfNewRoom = ucb.path("/api/rooms/{roomUuid}").buildAndExpand(savedRoom.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewRoom).build();
  }

  @PutMapping("/{roomUuid}")
  public ResponseEntity<Void> updateRoom(@PathVariable UUID roomUuid, @RequestBody Room roomToUpdate) {
    Room room = findRoom(roomUuid);
    if (room != null) {
      Room updatdeRoom = new Room(room.getId(), room.getUuid(), roomToUpdate.getName(), LocalDateTime.now(), room.getCreatedAt());
      roomRepository.save(updatdeRoom);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private Room findRoom(UUID roomUuid) {
    return roomRepository.findByUuid(roomUuid).orElse(null);
  }
}