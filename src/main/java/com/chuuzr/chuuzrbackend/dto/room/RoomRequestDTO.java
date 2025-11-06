package com.chuuzr.chuuzrbackend.dto.room;

/**
 * DTO for creating or updating a Room.
 * Does not include UUID or timestamps as these are managed by the server.
 */
public class RoomRequestDTO {
  private String name;

  public RoomRequestDTO() {
  }

  public RoomRequestDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
