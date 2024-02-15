package com.movienight.movienightbackend.models;

import java.sql.Date;

import com.google.auto.value.AutoValue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@AutoValue
@Entity
public abstract class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public abstract Long id();
  public abstract String name();
  public abstract Date updatedAt();
  public abstract Date createdAt();

  public static Room create(Long id, String name, Date updatedAt, Date createdAt) {
    return new AutoValue_Room(id, name, updatedAt, createdAt);
  }
}
