package com.movienight.movienightbackend.models;

import java.sql.Date;

import com.google.auto.value.AutoValue;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@AutoValue
@Entity
public abstract class UserVote {
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "room_id", referencedColumnName = "room_id"),
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  })
  public abstract RoomUser roomUser();
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "room_id", referencedColumnName = "room_id"),
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
  })
  public abstract RoomMovie roomMovie();
  public abstract VoteType voteType();
  public abstract Date updatedAt();
  public abstract Date createdAt();

  public static UserVote create(RoomUser roomUser, RoomMovie roomMovie, VoteType voteType, Date updatedAt, Date createdAt) {
    return new AutoValue_UserVote(roomUser, roomMovie, voteType, updatedAt, createdAt);
  }

  public enum VoteType {
    UP, DOWN, NONE
  }
}
