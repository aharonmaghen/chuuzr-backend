package com.chuuzr.chuuzrbackend.dto.roomuser;

import com.chuuzr.chuuzrbackend.dto.room.RoomMapper;
import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.User;

/**
 * Mapper utility for converting between RoomUser entity and DTOs.
 */
public class RoomUserMapper {

  /**
   * Converts a RoomUser entity to a RoomUserResponseDTO.
   */
  public static RoomUserResponseDTO toResponseDTO(RoomUser roomUser) {
    if (roomUser == null) {
      return null;
    }
    return new RoomUserResponseDTO(
        roomUser.getUuid(),
        RoomMapper.toResponseDTO(roomUser.getRoom()),
        UserMapper.toResponseDTO(roomUser.getUser()),
        roomUser.getUpdatedAt(),
        roomUser.getCreatedAt());
  }

  /**
   * Creates a RoomUser entity from Room and User entities.
   * The composite key will be set automatically.
   */
  public static RoomUser toEntity(Room room, User user) {
    if (room == null || user == null) {
      return null;
    }
    RoomUser roomUser = new RoomUser();
    roomUser.setRoom(room);
    roomUser.setUser(user);
    return roomUser;
  }
}
