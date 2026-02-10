package com.chuuzr.chuuzrbackend.dto.search;

public class SearchResultDTO {
  private String apiProvider;
  private String externalId;
  private String name;
  private String description;
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
