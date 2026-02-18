package com.chuuzr.chuuzrbackend.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Individual search result")
public class SearchResultDTO {
  @Schema(description = "External API provider identifier", example = "tmdb")
  private String apiProvider;

  @Schema(description = "Identifier in the external API", example = "550")
  private String externalId;

  @Schema(description = "Name of the result", example = "Fight Club")
  private String name;

  @Schema(description = "Description of the result", example = "An insomniac office worker and a devil-may-care soap maker form an underground fight club.")
  private String description;

  @Schema(description = "URL to the result image", example = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg", format = "uri")
  private String imageUrl;

  public SearchResultDTO() {
  }

  public SearchResultDTO(String apiProvider, String externalId, String name, String description, String imageUrl) {
    this.apiProvider = apiProvider;
    this.externalId = externalId;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
  }

  public String getApiProvider() {
    return apiProvider;
  }

  public void setApiProvider(String apiProvider) {
    this.apiProvider = apiProvider;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
