package com.chuuzr.chuuzrbackend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  private String name;

  @ManyToOne
  @JoinColumn(name = "option_type_id", nullable = false)
  private OptionType optionType;

  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public Room() {
  }

  public Room(Long id, UUID uuid, String name, OptionType optionType, LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.optionType = optionType;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OptionType getOptionType() {
    return optionType;
  }

  public void setOptionType(OptionType optionType) {
    this.optionType = optionType;
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
    return "Room{id=" + this.id +
        ", uuid=" + this.uuid +
        ", name=" + this.name +
        ", optionType=" + (optionType != null ? optionType.getName() : null) +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  @Override
  public boolean equals(Object room) {
    if (room == this) {
      return true;
    } else if (!(room instanceof Room)) {
      return false;
    } else {
      Room that = (Room) room;
      return this.id != null && this.id.equals(that.getId());
    }
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (this.uuid != null ? this.uuid.hashCode() : 0);
    return h$;
  }
}
