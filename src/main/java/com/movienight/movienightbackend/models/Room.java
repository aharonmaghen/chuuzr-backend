package com.movienight.movienightbackend.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a room.
 */
@Entity
@Table(name = "rooms")
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Date updatedAt;
  private Date createdAt;

  /**
   * Default constructor for Room required by JPA.
   */
  public Room() {
  }

  /**
   * Creates a new Room instance.
   *
   * @param id        The ID of the room.
   * @param name      The name of the room.
   * @param updatedAt The last updated date of the room.
   * @param createdAt The creation date of the room.
   */
  public Room(Long id, String name, Date updatedAt, Date createdAt) {
    this.id = id;
    this.name = name;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  /**
   * Returns the ID of the room.
   *
   * @return The ID of the room.
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the name of the room.
   *
   * @return The name of the room.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the last updated date of the room.
   *
   * @return The last updated date of the room.
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated date of the room.
   *
   * @param updatedAt The last updated date of the room.
   */
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the creation date of the room.
   *
   * @return The creation date of the room.
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation date of the room.
   *
   * @param createdAt The creation date of the room.
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the Room object.
   *
   * @return A string representation of the Room object.
   */
  @Override
  public String toString() {
    return "Room{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", updatedAt=" + updatedAt +
        ", createdAt=" + createdAt +
        '}';
  }
}