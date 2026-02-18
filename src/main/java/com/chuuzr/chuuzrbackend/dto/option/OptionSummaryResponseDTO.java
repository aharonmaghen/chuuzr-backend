package com.chuuzr.chuuzrbackend.dto.option;

import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary option information response")
public class OptionSummaryResponseDTO {
  @Schema(description = "Unique identifier for the option", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
  private UUID uuid;

  @Schema(description = "The option type this option belongs to")
  private OptionTypeResponseDTO optionType;

  @Schema(description = "External API provider identifier", example = "tmdb")
  private String apiProvider;

  @Schema(description = "Identifier in the external API", example = "550")
  private String externalId;

  @Schema(description = "Name of the option", example = "Fight Club")
  private String name;

  @Schema(description = "Description of the option", example = "An insomniac office worker and a devil-may-care soap maker form an underground fight club.")
  private String description;

  @Schema(description = "URL to the option image", example = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg", format = "uri")
  private String imageUrl;

  public OptionSummaryResponseDTO() {
  }

  public OptionSummaryResponseDTO(UUID uuid, OptionTypeResponseDTO optionType, String apiProvider, String externalId,
      String name, String description, String imageUrl) {
    this.uuid = uuid;
    this.optionType = optionType;
    this.apiProvider = apiProvider;
    this.externalId = externalId;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
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

}
