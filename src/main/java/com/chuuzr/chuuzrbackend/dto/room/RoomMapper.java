package com.chuuzr.chuuzrbackend.dto.room;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeMapper;
import com.chuuzr.chuuzrbackend.model.Room;

public class RoomMapper {

  public static RoomResponseDTO toResponseDTO(Room room) {
    if (room == null) {
      return null;
    }
    return new RoomResponseDTO(
        room.getUuid(),
        room.getName(),
        OptionTypeMapper.toResponseDTO(room.getOptionType()),
        room.getUpdatedAt(),
        room.getCreatedAt());
  }

  public static Room toEntity(RoomRequestDTO dto) {
    if (dto == null) {
      return null;
    }
    Room room = new Room();
    room.setName(dto.getName());
    return room;
  }

  public static void updateEntityFromDTO(Room room, RoomRequestDTO dto) {
    if (room == null || dto == null) {
      return;
    }
    room.setName(dto.getName());
  }
}
