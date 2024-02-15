package com.movienight.movienightbackend.models;

import java.sql.Date;
import java.util.Optional;

import com.google.auto.value.AutoValue;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a relationship between a room and a user.
 */
@AutoValue
@Entity
public abstract class RoomUser {
  /**
   * Returns the room associated with the room-user relationship.
   *
   * @return The room associated with the room-user relationship.
   */
  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  public abstract Room room();

  /**
   * Returns the user associated with the room-user relationship.
   *
   * @return The user associated with the room-user relationship.
   */
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  public abstract User user();

  /**
   * Returns the optional last updated date of the room-user relationship.
   *
   * @return The optional last updated date of the room-user relationship.
   */
  public abstract Optional<Date> updatedAt();

  /**
   * Returns the optional creation date of the room-user relationship.
   *
   * @return The optional creation date of the room-user relationship.
   */
  public abstract Optional<Date> createdAt();

  /**
   * Creates a new RoomUser instance.
   *
   * @param room      The room associated with the room-user relationship.
   * @param user      The user associated with the room-user relationship.
   * @param updatedAt The optional last updated date of the room-user
   *                  relationship.
   * @param createdAt The optional creation date of the room-user relationship.
   * @return A new RoomUser instance.
   */
  public static RoomUser create(Room room, User user, Optional<Date> updatedAt, Optional<Date> createdAt) {
    return new AutoValue_RoomUser(room, user, updatedAt, createdAt);
  }
}