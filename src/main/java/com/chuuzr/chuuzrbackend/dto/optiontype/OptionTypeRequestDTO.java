package com.chuuzr.chuuzrbackend.dto.optiontype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.chuuzr.chuuzrbackend.util.validation.annotations.ValidName;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Option type creation or update request")
public class OptionTypeRequestDTO {
  @Schema(description = "Name of the option type", example = "Movies", minLength = 1, maxLength = 50)
  @NotBlank(message = "Name is required")
  @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
  @ValidName
  private String name;

  @Schema(description = "Description of the option type", example = "Feature films and documentaries", minLength = 1, maxLength = 500)
  @NotBlank(message = "Description is required")
  @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
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
