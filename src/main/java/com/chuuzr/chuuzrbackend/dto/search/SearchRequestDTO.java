package com.chuuzr.chuuzrbackend.dto.search;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Search request payload")
public class SearchRequestDTO {
  @Schema(description = "Search query string", example = "Fight Club", minLength = 1, maxLength = 200)
  @NotBlank(message = "Search query is required")
  @Size(min = 1, max = 200, message = "Search query must be between 1 and 200 characters")
  private String query;

  public SearchRequestDTO() {
  }

  public SearchRequestDTO(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

}
