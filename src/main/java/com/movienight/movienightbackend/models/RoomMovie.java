package com.movienight.movienightbackend.models;

import java.sql.Date;

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
  private Date updatedAt;
  private Date createdAt;

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
   * @param updatedAt The last updated date of the room-movie relationship.
   * @param createdAt The creation date of the room-movie relationship.
   */
  public RoomMovie(Room room, Movie movie, Date updatedAt, Date createdAt) {
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
   * Returns the last updated date of the room-movie relationship.
   *
   * @return The last updated date of the room-movie relationship.
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated date of the room-movie relationship.
   *
   * @param updatedAt The last updated date of the room-movie relationship.
   */
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the creation date of the room-movie relationship.
   *
   * @return The creation date of the room-movie relationship.
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the optional creation date of the room-movie relationship.
   *
   * @param createdAt The optional creation date of the room-movie relationship.
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the RoomMovie object.
   *
   * @return A string representation of the RoomMovie object.
   */
  @Override
  public String toString() {
    return "RoomMovie{" +
        "room=" + room +
        ", movie=" + movie +
        ", updatedAt=" + updatedAt +
        ", createdAt=" + createdAt +
        '}';
  }
}