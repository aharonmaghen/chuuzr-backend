package com.chuuzr.chuuzrbackend.dto.roomoption;

import com.chuuzr.chuuzrbackend.dto.option.OptionMapper;
import com.chuuzr.chuuzrbackend.dto.room.RoomMapper;
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomOption;

/**
 * Mapper utility for converting between RoomOption entity and DTOs.
 */
public class RoomOptionMapper {

  /**
   * Converts a RoomOption entity to a RoomOptionResponseDTO.
   */
  public static RoomOptionResponseDTO toResponseDTO(RoomOption roomOption) {
    if (roomOption == null) {
      return null;
    }
    return new RoomOptionResponseDTO(
        RoomMapper.toResponseDTO(roomOption.getRoom()),
        OptionMapper.toResponseDTO(roomOption.getOption()),
        roomOption.getUpdatedAt(),
        roomOption.getCreatedAt());
  }

  /**
   * Creates a RoomOption entity from Room and Option entities.
   * The composite key will be set automatically.
   */
  public static RoomOption toEntity(Room room, Option option) {
    if (room == null || option == null) {
      return null;
    }
    RoomOption roomOption = new RoomOption();
    roomOption.setRoom(room);
    roomOption.setOption(option);
    return roomOption;
  }
}
