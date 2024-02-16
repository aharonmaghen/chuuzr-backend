package com.movienight.movienightbackend.models;

import java.sql.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a vote by a user in a room for a particular movie.
 */
@Entity
@Table(name = "user_votes")
public class UserVote {
  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "room_id", referencedColumnName = "room_id"),
      @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  })
  @EmbeddedId
  private RoomUser roomUser;

  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "room_id", referencedColumnName = "room_id"),
      @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
  })
  @EmbeddedId
  private RoomMovie roomMovie;
  private VoteType voteType;
  private Date updatedAt;
  private Date createdAt;

  /**
   * Default constructor for UserVote required by JPA.
   */
  public UserVote() {
  }

  /**
   * Constructs a new UserVote instance.
   *
   * @param roomUser  The room-user relationship associated with the user vote.
   * @param roomMovie The room-movie relationship associated with the user vote.
   * @param voteType  The type of vote (UP, DOWN, or NONE).
   * @param updatedAt The last updated date of the user vote.
   * @param createdAt The creation date of the user vote.
   */
  public UserVote(RoomUser roomUser, RoomMovie roomMovie, VoteType voteType, Date updatedAt, Date createdAt) {
    this.roomUser = roomUser;
    this.roomMovie = roomMovie;
    this.voteType = voteType;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  /**
   * Returns the room-user relationship associated with the user vote.
   *
   * @return The room-user relationship associated with the user vote.
   */
  public RoomUser getRoomUser() {
    return roomUser;
  }

  /**
   * Sets the room-user relationship associated with the user vote.
   *
   * @param roomUser The room-user relationship associated with the user vote.
   */
  public void setRoomUser(RoomUser roomUser) {
    this.roomUser = roomUser;
  }

  /**
   * Returns the room-movie relationship associated with the user vote.
   *
   * @return The room-movie relationship associated with the user vote.
   */
  public RoomMovie getRoomMovie() {
    return roomMovie;
  }

  /**
   * Sets the room-movie relationship associated with the user vote.
   *
   * @param roomMovie The room-movie relationship associated with the user vote.
   */
  public void setRoomMovie(RoomMovie roomMovie) {
    this.roomMovie = roomMovie;
  }

  /**
   * Returns the type of vote (UP, DOWN, or NONE).
   *
   * @return The type of vote (UP, DOWN, or NONE).
   */
  public VoteType getVoteType() {
    return voteType;
  }

  /**
   * Sets the type of vote (UP, DOWN, or NONE).
   *
   * @param voteType The type of vote (UP, DOWN, or NONE).
   */
  public void setVoteType(VoteType voteType) {
    this.voteType = voteType;
  }

  /**
   * Returns the last updated date of the user vote.
   *
   * @return The last updated date of the user vote.
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated date of the user vote.
   *
   * @param updatedAt The last updated date of the user vote.
   */
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the creation date of the user vote.
   *
   * @return The creation date of the user vote.
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation date of the user vote.
   *
   * @param createdAt The creation date of the user vote.
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the UserVote object.
   *
   * @return A string representation of the UserVote object.
   */
  @Override
  public String toString() {
    return "UserVote{" +
        "roomUser=" + roomUser +
        ", roomMovie=" + roomMovie +
        ", voteType=" + voteType +
        ", updatedAt=" + updatedAt +
        ", createdAt=" + createdAt +
        '}';
  }

  /**
   * Represents the type of vote: UP, DOWN, or NONE.
   */
  public enum VoteType {
    UP, DOWN, NONE
  }
}