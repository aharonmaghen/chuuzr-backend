package com.movienight.movienightbackend.models;

import java.sql.Date;

import com.google.auto.value.AutoValue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@AutoValue
@Entity
public abstract class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public abstract Long id();
  public abstract String title();
  public abstract Integer year();
  public abstract String genre();
  public abstract Date createdAt();
  
  public static Movie create(Long id, String title, Integer year, String genre, Date createdAt) {
    return new AutoValue_Movie(id, title, year, genre, createdAt);
  }
}
