package com.chuuzr.chuuzrbackend.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidName;

public class RoomRequestDTO {
  @NotBlank(message = "Room name is required")
  @Size(min = 1, max = 100, message = "Room name must be between 1 and 100 characters")
  @ValidName
  private String name;

  @NotNull(message = "Option type UUID is required")
  private UUID optionTypeUuid;

  public RoomRequestDTO() {
  }

  public RoomRequestDTO(String name, UUID optionTypeUuid) {
    this.name = name;
    this.optionTypeUuid = optionTypeUuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getOptionTypeUuid() {
    return optionTypeUuid;
  }

  public void setOptionTypeUuid(UUID optionTypeUuid) {
    this.optionTypeUuid = optionTypeUuid;
  }
}
