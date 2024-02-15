package com.movienight.movienightbackend.models;

import java.sql.Date;
import java.util.Optional;

import com.google.auto.value.AutoValue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a movie.
 */
@AutoValue
@Entity
public abstract class Movie {
  /**
   * Returns the ID of the movie.
   *
   * @return The ID of the movie.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public abstract Long id();

  /**
   * Returns the title of the movie.
   *
   * @return The title of the movie.
   */
  public abstract String title();

  /**
   * Returns the release year of the movie.
   *
   * @return The release year of the movie.
   */
  public abstract Integer year();

  /**
   * Returns the genre of the movie.
   *
   * @return The genre of the movie.
   */
  public abstract String genre();

  /**
   * Returns the optional creation date of the movie.
   *
   * @return The optional creation date of the movie.
   */
  public abstract Optional<Date> createdAt();

  /**
   * Creates a new Movie instance.
   *
   * @param id        The ID of the movie.
   * @param title     The title of the movie.
   * @param year      The release year of the movie.
   * @param genre     The genre of the movie.
   * @param createdAt The optional creation date of the movie.
   * @return A new Movie instance.
   */
  public static Movie create(
      Long id,
      String title,
      Integer year,
      String genre,
      Optional<Date> createdAt) {
    return new AutoValue_Movie(id, title, year, genre, createdAt);
  }
}