package com.movienight.movienightbackend.models;

import java.sql.Date;
import java.util.Optional;

import com.google.auto.value.AutoValue;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a relationship between a room and a movie.
 */
@AutoValue
@Entity
public abstract class RoomMovie {
  /**
   * Returns the room associated with the room-movie relationship.
   *
   * @return The room associated with the room-movie relationship.
   */
  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  public abstract Room room();

  /**
   * Returns the movie associated with the room-movie relationship.
   *
   * @return The movie associated with the room-movie relationship.
   */
  @ManyToOne
  @JoinColumn(name = "movie_id", referencedColumnName = "id")
  public abstract Movie movie();

  /**
   * Returns the optional last updated date of the room-movie relationship.
   *
   * @return The optional last updated date of the room-movie relationship.
   */
  public abstract Optional<Date> updatedAt();

  /**
   * Returns the optional creation date of the room-movie relationship.
   *
   * @return The optional creation date of the room-movie relationship.
   */
  public abstract Optional<Date> createdAt();

  /**
   * Creates a new RoomMovie instance.
   *
   * @param room      The room associated with the room-movie relationship.
   * @param movie     The movie associated with the room-movie relationship.
   * @param updatedAt The optional last updated date of the room-movie
   *                  relationship.
   * @param createdAt The optional creation date of the room-movie relationship.
   * @return A new RoomMovie instance.
   */
  public static RoomMovie create(
      Room room,
      Movie movie,
      Optional<Date> updatedAt,
      Optional<Date> createdAt) {
    return new AutoValue_RoomMovie(room, movie, updatedAt, createdAt);
  }
}