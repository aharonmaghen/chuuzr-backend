package com.movienight.movienightbackend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a movie.
 */
@Entity
@Table(name = "movies")
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private Integer year;
  private String genre;
  private LocalDateTime createdAt;

  /**
   * Default constructor for Movie required by JPA.
   */
  public Movie() {
  }

  /**
   * Creates a new Movie instance.
   *
   * @param id        The ID of the movie.
   * @param title     The title of the movie.
   * @param year      The release year of the movie.
   * @param genre     The genre of the movie.
   * @param createdAt The creation timestamp of the movie.
   */
  public Movie(Long id, String title, Integer year, String genre, LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.year = year;
    this.genre = genre;
    this.createdAt = createdAt;
  }

  /**
   * Returns the ID of the movie.
   *
   * @return The ID of the movie.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the movie.
   *
   * @param id The ID of the movie.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the title of the movie.
   *
   * @return The title of the movie.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the movie.
   *
   * @param title The title of the movie.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Returns the release year of the movie.
   *
   * @return The release year of the movie.
   */
  public Integer getYear() {
    return year;
  }

  /**
   * Sets the release year of the movie.
   *
   * @param year The release year of the movie.
   */
  public void setYear(Integer year) {
    this.year = year;
  }

  /**
   * Returns the genre of the movie.
   *
   * @return The genre of the movie.
   */
  public String getGenre() {
    return genre;
  }

  /**
   * Sets the genre of the movie.
   *
   * @param genre The genre of the movie.
   */
  public void setGenre(String genre) {
    this.genre = genre;
  }

  /**
   * Returns the creation timestamp of the movie.
   *
   * @return The creation timestamp of the movie.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation timestamp of the movie.
   *
   * @param createdAt The creation timestamp of the movie.
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the Movie object.
   *
   * @return A string representation of the Movie object.
   */
  @Override
  public String toString() {
    return "Movie{id=" + this.id +
        ", title=" + this.title +
        ", year=" + this.year +
        ", genre=" + this.genre +
        ", createdAt=" + this.createdAt + "}";
  }

  /**
   * Indicates whether a movie is "equal to" this one.
   *
   * @param movie the movie with which to compare.
   * @return true if this movie is the same as the movie argument; false
   *         otherwise.
   */
  public boolean equals(Object movie) {
    if (movie == this) {
      return true;
    } else if (!(movie instanceof Movie)) {
      return false;
    } else {
      Movie that = (Movie) movie;
      return this.id.equals(that.getId()) &&
          this.title.equals(that.getTitle()) &&
          this.year.equals(that.getYear()) &&
          this.genre.equals(that.getGenre()) &&
          this.createdAt.equals(that.getCreatedAt());
    }
  }

  /**
   * Returns a hash code value for the movie. This method is supported for the
   * benefit of hash tables such as those provided by HashMap.
   *
   * @return a hash code value for the movie.
   */
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.id.hashCode();
    h$ *= 1000003;
    h$ ^= this.title.hashCode();
    h$ *= 1000003;
    h$ ^= this.year.hashCode();
    h$ *= 1000003;
    h$ ^= this.genre.hashCode();
    h$ *= 1000003;
    h$ ^= this.createdAt.hashCode();
    return h$;
  }
}