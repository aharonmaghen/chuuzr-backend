package com.chuuzr.chuuzrbackend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "options")
public class Option {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;
  
  @ManyToOne
  @JoinColumn(name = "option_type_id")
  private OptionType optionType;

  private String apiProvider;
  private String externalId;
  private String name;
  private String description;
  private String imageUrl;
  
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public Option() {
  }

  public Option(Long id, UUID uuid, OptionType optionType, String apiProvider, String externalId, String name,
      String description, String imageUrl, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.id = id;
    this.uuid = uuid;
    this.optionType = optionType;
    this.apiProvider = apiProvider;
    this.externalId = externalId;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  @PrePersist
  private void prePersist() {
    if (uuid == null) {
      uuid = UUID.randomUUID();
    }
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
    if (updatedAt == null) {
      updatedAt = LocalDateTime.now();
    }
  }

  @PreUpdate
  private void preUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public OptionType getOptionType() {
    return optionType;
  }

  public void setOptionType(OptionType optionType) {
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

  @Override
  public String toString() {
    return "Option{id=" + this.id +
        ", uuid=" + this.uuid +
        ", optionType=" + (optionType != null ? optionType.getName() : null) +
        ", apiProvider=" + this.apiProvider +
        ", externalId=" + this.externalId +
        ", name=" + this.name +
        ", description=" + this.description +
        ", imageUrl=" + this.imageUrl +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  public boolean equals(Object option) {
    if (option == this) {
      return true;
    } else if (!(option instanceof Option)) {
      return false;
    } else {
      Option that = (Option) option;
      return this.id.equals(that.getId()) &&
          this.uuid.equals(that.getUuid()) &&
          this.name.equals(that.getName()) &&
          this.createdAt.equals(that.getCreatedAt());
    }
  }

  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.id.hashCode();
    h$ *= 1000003;
    h$ ^= this.uuid.hashCode();
    h$ *= 1000003;
    h$ ^= this.name.hashCode();
    h$ *= 1000003;
    h$ ^= this.createdAt.hashCode();
    return h$;
  }
}