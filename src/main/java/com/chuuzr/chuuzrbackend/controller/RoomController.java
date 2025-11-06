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

import com.chuuzr.chuuzrbackend.dto.room.RoomMapper;
import com.chuuzr.chuuzrbackend.dto.room.RoomRequestDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
  private RoomRepository roomRepository;

  @Autowired
  public RoomController(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @GetMapping("/{roomUuid}")
  public ResponseEntity<RoomResponseDTO> findById(@PathVariable UUID roomUuid) {
    Room room = findRoom(roomUuid);
    if (room != null) {
      return ResponseEntity.ok(RoomMapper.toResponseDTO(room));
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Void> createRoom(@RequestBody RoomRequestDTO newRoomRequest, UriComponentsBuilder ucb) {
    Room roomToSave = RoomMapper.toEntity(newRoomRequest);
    Room savedRoom = roomRepository.save(roomToSave);
    URI locationOfNewRoom = ucb.path("/api/rooms/{roomUuid}").buildAndExpand(savedRoom.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewRoom).build();
  }

  @PutMapping("/{roomUuid}")
  public ResponseEntity<Void> updateRoom(@PathVariable UUID roomUuid, @RequestBody RoomRequestDTO roomToUpdate) {
    Room room = findRoom(roomUuid);
    if (room != null) {
      RoomMapper.updateEntityFromDTO(room, roomToUpdate);
      roomRepository.save(room);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private Room findRoom(UUID roomUuid) {
    return roomRepository.findByUuid(roomUuid).orElse(null);
  }
}