package com.chuuzr.chuuzrbackend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Represents a type of option (e.g., 'Movie', 'Restaurant', 'Park').
 */
@Entity
@Table(name = "option_types")
public class OptionType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  private String name;

  private String description;

  private LocalDateTime updatedAt;

  private LocalDateTime createdAt;

  /**
   * Default constructor for OptionType required by JPA.
   */
  public OptionType() {
  }

  /**
   * Constructs a new OptionType instance.
   *
   * @param id          The ID of the option type.
   * @param uuid        The UUID of the option type.
   * @param name        The name of the option type.
   * @param description The description of the option type.
   * @param updatedAt   The last updated timestamp of the option type.
   * @param createdAt   The creation timestamp of the option type.
   */
  public OptionType(Long id, UUID uuid, String name, String description, LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.description = description;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  @PrePersist
  private void generateUuidAndCreatedAt() {
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
  private void updateTimestamp() {
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
    return "OptionType{id=" + this.id +
        ", uuid=" + this.uuid +
        ", name=" + this.name +
        ", description=" + this.description +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  public boolean equals(Object optionType) {
    if (optionType == this) {
      return true;
    } else if (!(optionType instanceof OptionType)) {
      return false;
    } else {
      OptionType that = (OptionType) optionType;
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

