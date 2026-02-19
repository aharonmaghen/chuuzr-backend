package com.chuuzr.chuuzrbackend.dto.roomoption;

import com.chuuzr.chuuzrbackend.dto.option.OptionMapper;
import com.chuuzr.chuuzrbackend.dto.room.RoomMapper;
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomOption;

public class RoomOptionMapper {

  public static RoomOptionResponseDTO toResponseDTO(RoomOption roomOption) {
    if (roomOption == null) {
      return null;
    }
    return new RoomOptionResponseDTO(
        RoomMapper.toResponseDTO(roomOption.getRoom()),
        OptionMapper.toSummaryDTO(roomOption.getOption()),
        roomOption.getScore(),
        roomOption.getUpdatedAt(),
        roomOption.getCreatedAt());
  }

  public static RoomOptionSummaryResponseDTO toSummaryDTO(RoomOption roomOption) {
    if (roomOption == null) {
      return null;
    }
    return new RoomOptionSummaryResponseDTO(
        OptionMapper.toSummaryDTO(roomOption.getOption()),
        roomOption.getScore());
  }

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
