package com.movienight.movienightbackend.controllers;

import java.net.URI;

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

  @GetMapping("/{requestedId}")
  public ResponseEntity<Room> findById(@PathVariable Long requestedId) {
    Room room = findRoom(requestedId);
    if (room != null) {
      return ResponseEntity.ok(room);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Void> createRoom(@RequestBody Room newRoomRequest, UriComponentsBuilder ucb) {
    Room roomToSave = new Room(null, newRoomRequest.getName(), null, null);
    Room savedRoom = roomRepository.save(roomToSave);
    URI locationOfNewRoom = ucb.path("api/rooms/{id}").buildAndExpand(savedRoom.getId()).toUri();
    return ResponseEntity.created(locationOfNewRoom).build();
  }

  @PutMapping("/{requestedId}")
  public ResponseEntity<Void> updateRoom(@PathVariable Long requestedId, @RequestBody Room roomToUpdate) {
    Room room = findRoom(requestedId);
    if (room != null) {
      Room updatdeRoom = new Room(room.getId(), roomToUpdate.getName(), null, null);
      roomRepository.save(updatdeRoom);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private Room findRoom(Long id) {
    return roomRepository.findById(id).orElse(null);
  }
}