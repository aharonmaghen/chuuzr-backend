package com.movienight.movienightbackend.models;

import java.time.LocalDateTime;

import com.movienight.movienightbackend.models.compositeKeys.RoomUserId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
  }

  public RoomUser(Room room, User user) {
    this.roomUserId = new RoomUserId(room.getId(), user.getId());
    this.room = room;
    this.user = user;
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
    return "RoomUser{room=" + this.room +
        ", user=" + this.user +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  public boolean equals(Object roomUser) {
    if (roomUser == this) {
      return true;
    } else if (!(roomUser instanceof RoomUser)) {
      return false;
    } else {
      RoomUser that = (RoomUser) roomUser;
      return this.room.equals(that.getRoom()) &&
          this.user.equals(that.getUser()) &&
          this.updatedAt.equals(that.getUpdatedAt()) &&
          this.createdAt.equals(that.getCreatedAt());
    }
  }

  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.room.hashCode();
    h$ *= 1000003;
    h$ ^= this.user.hashCode();
    h$ *= 1000003;
    h$ ^= this.updatedAt.hashCode();
    h$ *= 1000003;
    h$ ^= this.createdAt.hashCode();
    return h$;
  }
}