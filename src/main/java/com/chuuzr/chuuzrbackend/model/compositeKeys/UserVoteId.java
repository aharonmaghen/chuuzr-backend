package com.chuuzr.chuuzrbackend.model.compositeKeys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Represents a composite key of room_id, user_id, and option_id for the UserVote entity.
 */
@Embeddable
public class UserVoteId implements Serializable {
  @Column(name = "room_id")
  private Long roomId;
  @Column(name = "user_id")
  private Long userId;
  @Column(name = "option_id")
  private Long optionId;

  /**
   * Default constructor for UserVoteId required by JPA.
   */
  public UserVoteId() {
  }

  /**
   * Constructs a new UserVoteId instance.
   *
   * @param roomId   the ID of the room
   * @param userId   the ID of the user
   * @param optionId the ID of the option
   */
  public UserVoteId(Long roomId, Long userId, Long optionId) {
    this.roomId = roomId;
    this.userId = userId;
    this.optionId = optionId;
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

  public Long getOptionId() {
    return optionId;
  }

  public void setOptionId(Long optionId) {
    this.optionId = optionId;
  }

  public String toString() {
    return "UserVoteId{roomId=" + this.roomId +
        ", userId=" + this.userId +
        ", optionId=" + this.optionId + "}";
  }

  public boolean equals(Object userVoteId) {
    if (userVoteId == this) {
      return true;
    } else if (!(userVoteId instanceof UserVoteId)) {
      return false;
    } else {
      UserVoteId that = (UserVoteId) userVoteId;
      return this.roomId.equals(that.getRoomId()) &&
          this.userId.equals(that.getUserId()) &&
          this.optionId.equals(that.getOptionId());
    }
  }

  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.roomId.hashCode();
    h$ *= 1000003;
    h$ ^= this.userId.hashCode();
    h$ *= 1000003;
    h$ ^= this.optionId.hashCode();
    return h$;
  }
}

