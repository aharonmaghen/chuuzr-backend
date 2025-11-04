package com.chuuzr.chuuzrbackend.model.compositeKeys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Represents a composite key of room_id and option_id for the RoomOption entity.
 */
@Embeddable
public class RoomOptionId implements Serializable {
  @Column(name = "room_id")
  private Long roomId;
  @Column(name = "option_id")
  private Long optionId;

  /**
   * Default constructor for RoomOptionId required by JPA.
   */
  public RoomOptionId() {
  }

  /**
   * Constructs a new RoomOptionId instance.
   *
   * @param roomId   the ID of the room
   * @param optionId the ID of the option
   */
  public RoomOptionId(Long roomId, Long optionId) {
    this.roomId = roomId;
    this.optionId = optionId;
  }

  public Long getRoomId() {
    return roomId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public Long getOptionId() {
    return optionId;
  }

  public void setOptionId(Long optionId) {
    this.optionId = optionId;
  }

  public String toString() {
    return "RoomOptionId{roomId=" + this.roomId +
        ", optionId=" + this.optionId + "}";
  }

  public boolean equals(Object roomOptionId) {
    if (roomOptionId == this) {
      return true;
    } else if (!(roomOptionId instanceof RoomOptionId)) {
      return false;
    } else {
      RoomOptionId that = (RoomOptionId) roomOptionId;
      return this.roomId.equals(that.getRoomId()) &&
          this.optionId.equals(that.getOptionId());
    }
  }

  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.roomId.hashCode();
    h$ *= 1000003;
    h$ ^= this.optionId.hashCode();
    return h$;
  }
}
