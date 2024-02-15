package com.movienight.movienightbackend.models;

import java.sql.Date;

import com.google.auto.value.AutoValue;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@AutoValue
@Entity
public abstract class RoomMovie {
  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  public abstract Room room();
  @ManyToOne
  @JoinColumn(name = "movie_id", referencedColumnName = "id")
  public abstract Movie movie();
  public abstract Date updatedAt();
  public abstract Date createdAt();

  public static RoomMovie create(Room room, Movie movie, Date updatedAt, Date createdAt) {
    return new AutoValue_RoomMovie(room, movie, updatedAt, createdAt);
  }
}
