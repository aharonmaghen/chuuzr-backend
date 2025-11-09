package com.chuuzr.chuuzrbackend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.model.compositekeys.RoomOptionId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Represents a relationship between a room and an option.
 */
@Entity
@Table(name = "room_options")
public class RoomOption {
  @EmbeddedId
  private RoomOptionId roomOptionId;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  @ManyToOne
  @MapsId("roomId")
  private Room room;

  @ManyToOne
  @MapsId("optionId")
  private Option option;

  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  /**
   * Default constructor for RoomOption required by JPA.
   */
  public RoomOption() {
    this.roomOptionId = new RoomOptionId();
  }

  /**
   * Constructs a new RoomOption instance.
   * <p>
   * Note: The composite key (roomOptionId) is automatically initialized.
   * The actual key values are set when the room and option are assigned.
   *
   * @param uuid      The UUID of the room-option relationship.
   * @param room      The room associated with the room-option relationship.
   * @param option    The option associated with the room-option relationship.
   * @param updatedAt The last updated timestamp of the room-option relationship.
   * @param createdAt The creation timestamp of the room-option relationship.
   */
  public RoomOption(UUID uuid, Room room, Option option, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.roomOptionId = new RoomOptionId();
    this.uuid = uuid;
    this.room = room;
    this.option = option;
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

  public RoomOptionId getRoomOptionId() {
    return roomOptionId;
  }

  public void setRoomOptionId(RoomOptionId roomOptionId) {
    this.roomOptionId = roomOptionId;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public Option getOption() {
    return option;
  }

  public void setOption(Option option) {
    this.option = option;
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
    return "RoomOption{uuid=" + this.uuid +
        ", roomUuid=" + (room != null ? room.getUuid() : null) +
        ", optionUuid=" + (option != null ? option.getUuid() : null) +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  @Override
  public boolean equals(Object roomOption) {
    if (roomOption == this) {
      return true;
    } else if (!(roomOption instanceof RoomOption)) {
      return false;
    } else {
      RoomOption that = (RoomOption) roomOption;
      return this.roomOptionId.equals(that.getRoomOptionId());
    }
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.roomOptionId.hashCode();
    return h$;
  }
}
