package com.movienight.movienightbackend.models;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a relationship between a room and a movie.
 */
@Entity
@Table(name = "room_movies")
public class RoomMovie {
  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  @EmbeddedId
  private Room room;

  @ManyToOne
  @JoinColumn(name = "movie_id", referencedColumnName = "id")
  @EmbeddedId
  private Movie movie;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  /**
   * Default constructor for RoomMovie required by JPA.
   */
  public RoomMovie() {
  }

  /**
   * Constructs a new RoomMovie instance.
   *
   * @param room      The room associated with the room-movie relationship.
   * @param movie     The movie associated with the room-movie relationship.
   * @param updatedAt The last updated timestamp of the room-movie relationship.
   * @param createdAt The creation timestamp of the room-movie relationship.
   */
  public RoomMovie(Room room, Movie movie, LocalDateTime updatedAt, LocalDateTime createdAt) {
    this.room = room;
    this.movie = movie;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  /**
   * Returns the room associated with the room-movie relationship.
   *
   * @return The room associated with the room-movie relationship.
   */
  public Room getRoom() {
    return room;
  }

  /**
   * Sets the room associated with the room-movie relationship.
   *
   * @param room The room associated with the room-movie relationship.
   */
  public void setRoom(Room room) {
    this.room = room;
  }

  /**
   * Returns the movie associated with the room-movie relationship.
   *
   * @return The movie associated with the room-movie relationship.
   */
  public Movie getMovie() {
    return movie;
  }

  /**
   * Sets the movie associated with the room-movie relationship.
   *
   * @param movie The movie associated with the room-movie relationship.
   */
  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  /**
   * Returns the last updated timestamp of the room-movie relationship.
   *
   * @return The last updated timestamp of the room-movie relationship.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated timestamp of the room-movie relationship.
   *
   * @param updatedAt The last updated timestamp of the room-movie relationship.
   */
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the creation timestamp of the room-movie relationship.
   *
   * @return The creation timestamp of the room-movie relationship.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the optional creation timestamp of the room-movie relationship.
   *
   * @param createdAt The optional creation timestamp of the room-movie
   *                  relationship.
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the RoomMovie object.
   *
   * @return A string representation of the RoomMovie object.
   */
  @Override
  public String toString() {
    return "RoomMovie{room=" + this.room +
        ", movie=" + this.movie +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  /**
   * Indicates whether the RoomMovie object is "equal to" this one.
   *
   * @param roomMovie the RoomMovie object with which to compare.
   * @return true if this RoomMovie is the same as the roomMovie argument; false
   *         otherwise.
   */
  public boolean equals(Object roomMovie) {
    if (roomMovie == this) {
      return true;
    } else if (!(roomMovie instanceof RoomMovie)) {
      return false;
    } else {
      RoomMovie that = (RoomMovie) roomMovie;
      return this.room.equals(that.getRoom()) &&
          this.movie.equals(that.getMovie()) &&
          this.updatedAt.equals(that.getUpdatedAt()) &&
          this.createdAt.equals(that.getCreatedAt());
    }
  }

  /**
   * Returns a hash code value for the RoomMovie object. This method is supported for the
   * benefit of hash tables such as those provided by HashMap.
   *
   * @return a hash code value for the RoomMovie object.
   */
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.room.hashCode();
    h$ *= 1000003;
    h$ ^= this.movie.hashCode();
    h$ *= 1000003;
    h$ ^= this.updatedAt.hashCode();
    h$ *= 1000003;
    h$ ^= this.createdAt.hashCode();
    return h$;
  }
}