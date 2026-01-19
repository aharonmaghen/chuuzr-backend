package com.chuuzr.chuuzrbackend.model;

import java.time.LocalDateTime;

import com.chuuzr.chuuzrbackend.model.compositekeys.RoomUserId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "room_users")
public class RoomUser {
  @EmbeddedId
  private RoomUserId roomUserId;

  @ManyToOne
  @MapsId("roomId")
  private Room room;

  @ManyToOne
  @MapsId("userId")
  private User user;

  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public RoomUser() {
    this.roomUserId = new RoomUserId();
  }

  public RoomUser(Room room, User user, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.roomUserId = new RoomUserId();
    this.room = room;
    this.user = user;
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

  public RoomUserId getRoomUserId() {
    return roomUserId;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
    return "RoomUser{roomUuid=" + (room != null ? room.getUuid() : null) +
        ", userUuid=" + (user != null ? user.getUuid() : null) +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  @Override
  public boolean equals(Object roomUser) {
    if (roomUser == this) {
      return true;
    } else if (!(roomUser instanceof RoomUser)) {
      return false;
    } else {
      RoomUser that = (RoomUser) roomUser;
      return this.roomUserId.equals(that.getRoomUserId());
    }
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.roomUserId.hashCode();
    return h$;
  }
}