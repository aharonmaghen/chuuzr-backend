package com.chuuzr.chuuzrbackend.models;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Movie{id=" + this.id +
        ", title=" + this.title +
        ", year=" + this.year +
        ", genre=" + this.genre +
        ", createdAt=" + this.createdAt + "}";
  }

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