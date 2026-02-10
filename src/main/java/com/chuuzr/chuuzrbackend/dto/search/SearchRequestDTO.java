package com.chuuzr.chuuzrbackend.dto.search;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SearchRequestDTO {
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
