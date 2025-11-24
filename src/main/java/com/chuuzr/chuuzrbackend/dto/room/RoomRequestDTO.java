package com.chuuzr.chuuzrbackend.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.chuuzr.chuuzrbackend.util.validation.ValidName;

public class RoomRequestDTO {
  @NotBlank(message = "Room name is required")
  @Size(min = 1, max = 100, message = "Room name must be between 1 and 100 characters")
  @ValidName
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
