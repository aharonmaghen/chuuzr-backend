package com.movienight.movienightbackend.models;

import java.sql.Date;

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
  private Date createdAt;

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
   * @param createdAt The creation date of the movie.
   */
  public Movie(Long id, String title, Integer year, String genre, Date createdAt) {
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
   * Returns the creation date of the movie.
   *
   * @return The creation date of the movie.
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
     * Sets the creation date of the movie.
     *
     * @param createdAt The creation date of the movie.
     */
    public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the Movie object.
   *
   * @return A string representation of the Movie object.
   */
  @Override
  public String toString() {
    return "Movie{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", year=" + year +
        ", genre='" + genre + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}