package com.chuuzr.chuuzrbackend.dto.option;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;

public class OptionDetailResponseDTO {
  private UUID uuid;
  private OptionTypeResponseDTO optionType;
  private String apiProvider;
  private String externalId;
  private String name;
  private String description;
  private String imageUrl;
  private Map<String, Object> metadata;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public OptionDetailResponseDTO() {
  }

  public OptionDetailResponseDTO(UUID uuid, OptionTypeResponseDTO optionType, String apiProvider, String externalId,
      String name, String description, String imageUrl, Map<String, Object> metadata,
      LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.uuid = uuid;
    this.optionType = optionType;
    this.apiProvider = apiProvider;
    this.externalId = externalId;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.metadata = metadata;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public OptionTypeResponseDTO getOptionType() {
    return optionType;
  }

  public void setOptionType(OptionTypeResponseDTO optionType) {
    this.optionType = optionType;
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

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
