package com.chuuzr.chuuzrbackend.dto.roomuser;

import com.chuuzr.chuuzrbackend.dto.room.RoomMapper;
import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.User;

public class RoomUserMapper {

  public static RoomUserResponseDTO toResponseDTO(RoomUser roomUser) {
    if (roomUser == null) {
      return null;
    }
    return new RoomUserResponseDTO(
        RoomMapper.toResponseDTO(roomUser.getRoom()),
        UserMapper.toResponseDTO(roomUser.getUser()),
        roomUser.getUpdatedAt(),
        roomUser.getCreatedAt());
  }

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
