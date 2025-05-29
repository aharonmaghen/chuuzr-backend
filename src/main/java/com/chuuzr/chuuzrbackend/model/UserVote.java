package com.chuuzr.chuuzrbackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
  @Enumerated(EnumType.STRING)
  private VoteType voteType;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

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
   * @param updatedAt The last updated timestamp of the user vote.
   * @param createdAt The creation timestamp of the user vote.
   */
  public UserVote(RoomUser roomUser, RoomMovie roomMovie, VoteType voteType, LocalDateTime updatedAt,
      LocalDateTime createdAt) {
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
   * Returns the last updated timestamp of the user vote.
   *
   * @return The last updated timestamp of the user vote.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated timestamp of the user vote.
   *
   * @param updatedAt The last updated timestamp of the user vote.
   */
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the creation timestamp of the user vote.
   *
   * @return The creation timestamp of the user vote.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation timestamp of the user vote.
   *
   * @param createdAt The creation timestamp of the user vote.
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the UserVote object.
   *
   * @return A string representation of the UserVote object.
   */
  @Override
  public String toString() {
    return "UserVote{roomUser=" + this.roomUser +
        ", roomMovie=" + this.roomMovie +
        ", voteType=" + this.voteType +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  /**
   * Indicates whether the UserVote object is "equal to" this one.
   *
   * @param userVote the UserVote object with which to compare.
   * @return true if this UserVote is the same as the userVote argument; false
   *         otherwise.
   */
  public boolean equals(Object userVote) {
    if (userVote == this) {
      return true;
    } else if (!(userVote instanceof UserVote)) {
      return false;
    } else {
      UserVote that = (UserVote) userVote;
      return this.roomUser.equals(that.getRoomUser()) &&
          this.roomMovie.equals(that.getRoomMovie()) &&
          this.voteType.equals(that.getVoteType()) &&
          this.updatedAt.equals(that.getUpdatedAt()) &&
          this.createdAt.equals(that.getCreatedAt());
    }
  }

  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.roomUser.hashCode();
    h$ *= 1000003;
    h$ ^= this.roomMovie.hashCode();
    h$ *= 1000003;
    h$ ^= this.voteType.hashCode();
    h$ *= 1000003;
    h$ ^= this.updatedAt.hashCode();
    h$ *= 1000003;
    h$ ^= this.createdAt.hashCode();
    return h$;
  }

  public enum VoteType {
    UP, DOWN, NONE
  }
}