package com.chuuzr.chuuzrbackend.dto.search;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Search results response")
public class SearchResponseDTO {
  @Schema(description = "List of search results")
  private List<SearchResultDTO> results;

  public SearchResponseDTO() {
  }

  public SearchResponseDTO(List<SearchResultDTO> results) {
    this.results = results;
  }

  public List<SearchResultDTO> getResults() {
    return results;
  }

  public void setResults(List<SearchResultDTO> results) {
    this.results = results;
  }
}
