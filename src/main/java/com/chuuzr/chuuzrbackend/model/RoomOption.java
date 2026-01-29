package com.chuuzr.chuuzrbackend.model;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.model.compositekeys.RoomOptionId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "room_options")
public class RoomOption {
  @EmbeddedId
  private RoomOptionId roomOptionId;

  @ManyToOne
  @MapsId("roomId")
  private Room room;

  @ManyToOne
  @MapsId("optionId")
  private Option option;

  Integer score;

  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public RoomOption() {
    this.roomOptionId = new RoomOptionId();
  }

  public RoomOption(Room room, Option option, Integer score, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.roomOptionId = new RoomOptionId();
    this.room = room;
    this.option = option;
    this.score = score;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  @PrePersist
  private void prePersist() {
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

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
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
    return "RoomOption{roomUuid=" + (room != null ? room.getUuid() : null) +
        ", optionUuid=" + (option != null ? option.getUuid() : null) +
        ", score=" + this.score +
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
