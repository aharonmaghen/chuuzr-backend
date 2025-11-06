package com.chuuzr.chuuzrbackend.dto.room;

import com.chuuzr.chuuzrbackend.model.Room;

/**
 * Mapper utility for converting between Room entity and DTOs.
 */
public class RoomMapper {

  /**
   * Converts a Room entity to a RoomResponseDTO.
   */
  public static RoomResponseDTO toResponseDTO(Room room) {
    if (room == null) {
      return null;
    }
    return new RoomResponseDTO(
        room.getUuid(),
        room.getName(),
        room.getUpdatedAt(),
        room.getCreatedAt());
  }

  /**
   * Converts a RoomRequestDTO to a Room entity.
   * Note: ID, UUID, and timestamps should be set separately.
   */
  public static Room toEntity(RoomRequestDTO dto) {
    if (dto == null) {
      return null;
    }
    Room room = new Room();
    room.setName(dto.getName());
    return room;
  }

  /**
   * Updates an existing Room entity with data from RoomRequestDTO.
   * Preserves ID, UUID, and createdAt timestamp.
   */
  public static void updateEntityFromDTO(Room room, RoomRequestDTO dto) {
    if (room == null || dto == null) {
      return;
    }
    room.setName(dto.getName());
  }
}
