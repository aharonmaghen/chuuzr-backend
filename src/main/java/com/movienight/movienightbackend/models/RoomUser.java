package com.movienight.movienightbackend.models;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a relationship between a room and a user.
 */
@Entity
@Table(name = "room_users")
public class RoomUser {
  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  @EmbeddedId
  private Room room;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @EmbeddedId
  private User user;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  /**
   * Default constructor for RoomUser required by JPA.
   */
  public RoomUser() {
  }

  /**
   * Constructs a new RoomUser instance.
   *
   * @param room      The room associated with the room-user relationship.
   * @param user      The user associated with the room-user relationship.
   * @param updatedAt The last updated timestamp of the room-user relationship.
   * @param createdAt The creation timestamp of the room-user relationship.
   */
  public RoomUser(Room room, User user, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.room = room;
    this.user = user;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  /**
   * Returns the room associated with the room-user relationship.
   *
   * @return The room associated with the room-user relationship.
   */
  public Room getRoom() {
    return room;
  }

  /**
   * Sets the room associated with the room-user relationship.
   *
   * @param room The room associated with the room-user relationship.
   */
  public void setRoom(Room room) {
    this.room = room;
  }

  /**
   * Returns the user associated with the room-user relationship.
   *
   * @return The user associated with the room-user relationship.
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user associated with the room-user relationship.
   *
   * @param user The user associated with the room-user relationship.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Returns the optional last updated timestamp of the room-user relationship.
   *
   * @return The optional last updated timestamp of the room-user relationship.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the optional last updated timestamp of the room-user relationship.
   *
   * @param updatedAt The optional last updated timestamp of the room-user relationship.
   */
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the optional creation timestamp of the room-user relationship.
   *
   * @return The optional creation timestamp of the room-user relationship.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the optional creation timestamp of the room-user relationship.
   *
   * @param createdAt The optional creation timestamp of the room-user relationship.
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the RoomUser object.
   *
   * @return A string representation of the RoomUser object.
   */
  @Override
  public String toString() {
    return "RoomUser{room=" + this.room +
        ", user=" + this.user +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  /**
   * Indicates whether the RoomUser object is "equal to" this one.
   *
   * @param roomUser the RoomUser object with which to compare.
   * @return true if this RoomUser is the same as the roomUser argument; false
   *         otherwise.
   */
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

  /**
   * Returns a hash code value for the RoomUser object. This method is supported for the
   * benefit of hash tables such as those provided by HashMap.
   *
   * @return a hash code value for the RoomUser object.
   */
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