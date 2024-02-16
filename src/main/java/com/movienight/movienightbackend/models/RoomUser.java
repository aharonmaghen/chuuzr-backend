package com.movienight.movienightbackend.models;

import java.sql.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a relationship between a room and a user.
 */
@Entity
@Table(name = "room_users")
public class RoomUser {
  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  @EmbeddedId
  private Room room;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @EmbeddedId
  private User user;
  private Date updatedAt;
  private Date createdAt;

  /**
   * Default constructor for RoomUser required by JPA.
   */
  public RoomUser() {
  }

  /**
   * Constructs a new RoomUser instance.
   *
   * @param room      The room associated with the room-user relationship.
   * @param user      The user associated with the room-user relationship.
   * @param updatedAt The last updated date of the room-user relationship.
   * @param createdAt The creation date of the room-user relationship.
   */
  public RoomUser(Room room, User user, Date updatedAt, Date createdAt) {
    this.room = room;
    this.user = user;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  /**
   * Returns the room associated with the room-user relationship.
   *
   * @return The room associated with the room-user relationship.
   */
  public Room getRoom() {
    return room;
  }

  /**
   * Sets the room associated with the room-user relationship.
   *
   * @param room The room associated with the room-user relationship.
   */
  public void setRoom(Room room) {
    this.room = room;
  }

  /**
   * Returns the user associated with the room-user relationship.
   *
   * @return The user associated with the room-user relationship.
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user associated with the room-user relationship.
   *
   * @param user The user associated with the room-user relationship.
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Returns the optional last updated date of the room-user relationship.
   *
   * @return The optional last updated date of the room-user relationship.
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the optional last updated date of the room-user relationship.
   *
   * @param updatedAt The optional last updated date of the room-user relationship.
   */
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the optional creation date of the room-user relationship.
   *
   * @return The optional creation date of the room-user relationship.
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the optional creation date of the room-user relationship.
   *
   * @param createdAt The optional creation date of the room-user relationship.
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the RoomUser object.
   *
   * @return A string representation of the RoomUser object.
   */
  @Override
  public String toString() {
    return "RoomUser{" +
        "room=" + room +
        ", user=" + user +
        ", updatedAt=" + updatedAt +
        ", createdAt=" + createdAt +
        '}';
  }
}