package com.movienight.movienightbackend.models;

import java.sql.Date;
import java.util.Optional;

import com.google.auto.value.AutoValue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a room.
 */
@AutoValue
@Entity
public abstract class Room {
  /**
   * Returns the ID of the room.
   *
   * @return The ID of the room.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public abstract Long id();

  /**
   * Returns the name of the room.
   *
   * @return The name of the room.
   */
  public abstract String name();

  /**
   * Returns the optional last updated date of the room.
   *
   * @return The optional last updated date of the room.
   */
  public abstract Optional<Date> updatedAt();

  /**
   * Returns the optional creation date of the room.
   *
   * @return The optional creation date of the room.
   */
  public abstract Optional<Date> createdAt();

  /**
   * Creates a new Room instance.
   *
   * @param id        The ID of the room.
   * @param name      The name of the room.
   * @param updatedAt The optional last updated date of the room.
   * @param createdAt The optional creation date of the room.
   * @return A new Room instance.
   */
  public static Room create(Long id, String name, Optional<Date> updatedAt, Optional<Date> createdAt) {
    return new AutoValue_Room(id, name, updatedAt, createdAt);
  }
}