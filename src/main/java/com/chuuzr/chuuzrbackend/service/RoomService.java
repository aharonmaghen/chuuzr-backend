package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

  private final RoomRepository roomRepository;

  @Autowired
  public RoomService(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @Transactional(readOnly = true)
  public RoomResponseDTO findByUuid(UUID roomUuid) {
    logger.debug("Finding room by uuid={}", roomUuid);
    Room room = roomRepository.findByUuid(roomUuid).orElseThrow(
        () -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND, "Room with UUID " + roomUuid + " not found"));
    return RoomMapper.toResponseDTO(room);
  }

  public RoomResponseDTO createRoom(RoomRequestDTO roomRequestDTO) {
    logger.debug("Creating room");
    Room roomToSave = RoomMapper.toEntity(roomRequestDTO);
    Room savedRoom = roomRepository.save(roomToSave);
    logger.debug("Room saved with uuid={}", savedRoom.getUuid());
    return RoomMapper.toResponseDTO(savedRoom);
  }

  public RoomResponseDTO updateRoom(UUID roomUuid, RoomRequestDTO roomRequestDTO) {
    logger.debug("Updating room with uuid={}", roomUuid);
    Room room = roomRepository.findByUuid(roomUuid).orElseThrow(
        () -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND, "Room with UUID " + roomUuid + " not found"));
    RoomMapper.updateEntityFromDTO(room, roomRequestDTO);
    Room updatedRoom = roomRepository.save(room);
    logger.info("Room updated with uuid={}", roomUuid);
    return RoomMapper.toResponseDTO(updatedRoom);
  }
}
