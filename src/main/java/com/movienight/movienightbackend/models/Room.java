package com.movienight.movienightbackend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a room.
 */
@Entity
@Table(name = "rooms")
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  /**
   * Default constructor for Room required by JPA.
   */
  public Room() {
  }

  /**
   * Creates a new Room instance.
   *
   * @param id        The ID of the room.
   * @param name      The name of the room.
   * @param updatedAt The last updated timestamp of the room.
   * @param createdAt The creation timestamp of the room.
   */
  public Room(Long id, String name, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  /**
   * Returns the ID of the room.
   *
   * @return The ID of the room.
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the name of the room.
   *
   * @return The name of the room.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the last updated timestamp of the room.
   *
   * @return The last updated timestamp of the room.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated timestamp of the room.
   *
   * @param updatedAt The last updated timestamp of the room.
   */
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the creation timestamp of the room.
   *
   * @return The creation timestamp of the room.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation timestamp of the room.
   *
   * @param createdAt The creation timestamp of the room.
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the Room object.
   *
   * @return A string representation of the Room object.
   */
  @Override
  public String toString() {
    return "Room{id=" + this.id +
        ", name=" + this.name +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  /**
   * Indicates whether a room is "equal to" this one.
   *
   * @param room the room with which to compare.
   * @return true if this room is the same as the room argument; false
   *         otherwise.
   */
  public boolean equals(Object room) {
    if (room == this) {
      return true;
    } else if (!(room instanceof Room)) {
      return false;
    } else {
      Room that = (Room) room;
      return this.id.equals(that.getId()) &&
          this.name.equals(that.getName()) &&
          this.updatedAt.equals(that.getUpdatedAt()) &&
          this.createdAt.equals(that.getCreatedAt());
    }
  }

  /**
   * Returns a hash code value for the room. This method is supported for the
   * benefit of hash tables such as those provided by HashMap.
   *
   * @return a hash code value for the room.
   */
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.id.hashCode();
    h$ *= 1000003;
    h$ ^= this.name.hashCode();
    h$ *= 1000003;
    h$ ^= this.updatedAt.hashCode();
    h$ *= 1000003;
    h$ ^= this.createdAt.hashCode();
    return h$;
  }
}