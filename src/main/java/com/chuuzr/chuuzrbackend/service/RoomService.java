package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.room.RoomMapper;
import com.chuuzr.chuuzrbackend.dto.room.RoomRequestDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;

@Service
@Transactional
public class RoomService {

  private final RoomRepository roomRepository;

  @Autowired
  public RoomService(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @Transactional(readOnly = true)
  public RoomResponseDTO findByUuid(UUID roomUuid) {
    Room room = roomRepository.findByUuid(roomUuid).orElseThrow(
        () -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND, "Room with UUID " + roomUuid + " not found"));
    return RoomMapper.toResponseDTO(room);
  }

  public RoomResponseDTO createRoom(RoomRequestDTO roomRequestDTO) {
    Room roomToSave = RoomMapper.toEntity(roomRequestDTO);
    Room savedRoom = roomRepository.save(roomToSave);
    return RoomMapper.toResponseDTO(savedRoom);
  }

  public RoomResponseDTO updateRoom(UUID roomUuid, RoomRequestDTO roomRequestDTO) {
    Room room = roomRepository.findByUuid(roomUuid).orElseThrow(
        () -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND, "Room with UUID " + roomUuid + " not found"));
    RoomMapper.updateEntityFromDTO(room, roomRequestDTO);
    Room updatedRoom = roomRepository.save(room);
    return RoomMapper.toResponseDTO(updatedRoom);
  }
}
