package com.movienight.movienightbackend.models;

import java.sql.Date;

import com.google.auto.value.AutoValue;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@AutoValue
@Entity
public abstract class RoomUser {
  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  public abstract Room room();

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  public abstract User user();

  public abstract Date updatedAt();
  public abstract Date createdAt();

  public static RoomUser create(Room room, User user, Date updatedAt, Date createdAt) {
    return new AutoValue_RoomUser(room, user, updatedAt, createdAt);
  }
}
