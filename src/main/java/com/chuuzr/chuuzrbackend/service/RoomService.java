package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.room.RoomMapper;
import com.chuuzr.chuuzrbackend.dto.room.RoomRequestDTO;
import com.chuuzr.chuuzrbackend.dto.room.RoomResponseDTO;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;

/**
 * Service layer for Room business logic.
 * Handles transactions and coordinates between controller and repository.
 */
@Service
@Transactional
public class RoomService {

  private final RoomRepository roomRepository;

  @Autowired
  public RoomService(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  /**
   * Finds a room by UUID and returns the DTO.
   *
   * @param roomUuid The UUID of the room to find
   * @return RoomResponseDTO if found, null otherwise
   */
  @Transactional(readOnly = true)
  public RoomResponseDTO findByUuid(UUID roomUuid) {
    Room room = roomRepository.findByUuid(roomUuid).orElse(null);
    return RoomMapper.toResponseDTO(room);
  }

  /**
   * Creates a new room.
   *
   * @param roomRequestDTO The room data to create
   * @return The created room as a response DTO
   */
  public RoomResponseDTO createRoom(RoomRequestDTO roomRequestDTO) {
    Room roomToSave = RoomMapper.toEntity(roomRequestDTO);
    Room savedRoom = roomRepository.save(roomToSave);
    return RoomMapper.toResponseDTO(savedRoom);
  }

  /**
   * Updates an existing room.
   *
   * @param roomUuid       The UUID of the room to update
   * @param roomRequestDTO The updated room data
   * @return The updated room as a response DTO, or null if room not found
   */
  public RoomResponseDTO updateRoom(UUID roomUuid, RoomRequestDTO roomRequestDTO) {
    Room room = roomRepository.findByUuid(roomUuid).orElse(null);
    if (room == null) {
      return null;
    }
    RoomMapper.updateEntityFromDTO(room, roomRequestDTO);
    Room updatedRoom = roomRepository.save(room);
    return RoomMapper.toResponseDTO(updatedRoom);
  }
}
