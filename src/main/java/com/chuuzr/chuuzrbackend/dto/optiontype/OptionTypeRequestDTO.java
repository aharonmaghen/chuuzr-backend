package com.chuuzr.chuuzrbackend.dto.optiontype;

/**
 * DTO for creating or updating an OptionType.
 * Does not include UUID or timestamps as these are managed by the server.
 */
public class OptionTypeRequestDTO {
  private String name;
  private String description;

  public OptionTypeRequestDTO() {
  }

  public OptionTypeRequestDTO(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
