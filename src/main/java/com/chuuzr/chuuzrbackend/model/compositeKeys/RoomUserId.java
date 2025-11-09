package com.chuuzr.chuuzrbackend.model.compositekeys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Represents a composite key of room_id and user_id for the RoomUser entity.
 */
@Embeddable
public class RoomUserId implements Serializable {
  @Column(name = "room_id")
  private Long roomId;
  @Column(name = "user_id")
  private Long userId;

  /**
   * Default constructor for RoomUserId required by JPA.
   */
  public RoomUserId() {
  }

  /**
   * Constructs a new RoomUserId instance.
   *
   * @param roomId the ID of the room
   * @param userId the ID of the user
   */
  public RoomUserId(Long roomId, Long userId) {
    this.roomId = roomId;
    this.userId = userId;
  }

  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "RoomUserId{roomId=" + this.roomId +
        ", userId=" + this.userId + "}";
  }

  @Override
  public boolean equals(Object roomUserId) {
    if (roomUserId == this) {
      return true;
    } else if (!(roomUserId instanceof RoomUserId)) {
      return false;
    } else {
      RoomUserId that = (RoomUserId) roomUserId;
      return this.roomId.equals(that.getRoomId()) &&
          this.userId.equals(that.getUserId());
    }
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.roomId.hashCode();
    h$ *= 1000003;
    h$ ^= this.userId.hashCode();
    return h$;
  }
}
