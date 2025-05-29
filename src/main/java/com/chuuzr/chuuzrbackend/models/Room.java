package com.chuuzr.chuuzrbackend.models;

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
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public Room() {
  }

  public Room(Long id, UUID uuid, String name, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  @PrePersist
  private void generateUuid() {
    if (uuid == null) {
      uuid = UUID.randomUUID();
    }
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

  public String toString() {
    return "Room{id=" + this.id +
        ", uuid=" + this.uuid +
        ", name=" + this.name +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  public boolean equals(Object room) {
    if (room == this) {
      return true;
    } else if (!(room instanceof Room)) {
      return false;
    } else {
      Room that = (Room) room;
      return this.id.equals(that.getId()) &&
          this.uuid.equals(that.getUuid()) &&
          this.name.equals(that.getName()) &&
          this.updatedAt.equals(that.getUpdatedAt()) &&
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
    h$ ^= this.updatedAt.hashCode();
    h$ *= 1000003;
    h$ ^= this.createdAt.hashCode();
    return h$;
  }
}