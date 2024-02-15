package com.movienight.movienightbackend.models;

import java.sql.Date;
import java.util.Optional;

import com.google.auto.value.AutoValue;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

/**
 * Represents a vote by a user in a room for a particular movie.
 */
@AutoValue
@Entity
public abstract class UserVote {
  /**
   * Returns the room-user relationship associated with the user vote.
   *
   * @return The room-user relationship associated with the user vote.
   */
  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "room_id", referencedColumnName = "room_id"),
      @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  })
  @EmbeddedId
  public abstract RoomUser roomUser();

  /**
   * Returns the room-movie relationship associated with the user vote.
   *
   * @return The room-movie relationship associated with the user vote.
   */
  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "room_id", referencedColumnName = "room_id"),
      @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
  })
  @EmbeddedId
  public abstract RoomMovie roomMovie();

  /**
   * Returns the type of vote (UP, DOWN, or NONE).
   *
   * @return The type of vote (UP, DOWN, or NONE).
   */
  public abstract VoteType voteType();

  /**
   * Returns the optional last updated date of the user vote.
   *
   * @return The optional last updated date of the user vote.
   */
  public abstract Optional<Date> updatedAt();

  /**
   * Returns the optional creation date of the user vote.
   *
   * @return The optional creation date of the user vote.
   */
  public abstract Optional<Date> createdAt();

  /**
   * Creates a new UserVote instance.
   *
   * @param roomUser  The room-user relationship associated with the user vote.
   * @param roomMovie The room-movie relationship associated with the user vote.
   * @param voteType  The type of vote (UP, DOWN, or NONE).
   * @param updatedAt The optional last updated date of the user vote.
   * @param createdAt The optional creation date of the user vote.
   * @return A new UserVote instance.
   */
  public static UserVote create(
      RoomUser roomUser,
      RoomMovie roomMovie,
      VoteType voteType,
      Optional<Date> updatedAt,
      Optional<Date> createdAt) {
    return new AutoValue_UserVote(roomUser, roomMovie, voteType, updatedAt, createdAt);
  }

  /**
   * Represents the type of vote: UP, DOWN, or NONE.
   */
  public enum VoteType {
    UP, DOWN, NONE
  }
}