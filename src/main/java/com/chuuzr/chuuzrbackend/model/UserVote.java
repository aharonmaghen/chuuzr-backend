package com.chuuzr.chuuzrbackend.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.chuuzr.chuuzrbackend.model.compositekeys.UserVoteId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_votes")
public class UserVote {
  @EmbeddedId
  private UserVoteId userVoteId;

  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false),
      @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  })
  private RoomUser roomUser;

  @ManyToOne
  @JoinColumns({
      @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false),
      @JoinColumn(name = "option_id", referencedColumnName = "option_id", insertable = false, updatable = false)
  })
  private RoomOption roomOption;

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  @Column(name = "vote_type", columnDefinition = "vote_type")
  private VoteType voteType;

  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public UserVote() {
    this.userVoteId = new UserVoteId();
  }

  public UserVote(RoomUser roomUser, RoomOption roomOption, VoteType voteType, LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this.userVoteId = new UserVoteId();
    this.roomUser = roomUser;
    this.roomOption = roomOption;
    this.voteType = voteType;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  @PrePersist
  private void prePersist() {
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
    if (updatedAt == null) {
      updatedAt = LocalDateTime.now();
    }
  }

  @PreUpdate
  private void preUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public UserVoteId getUserVoteId() {
    return userVoteId;
  }

  public void setUserVoteId(UserVoteId userVoteId) {
    this.userVoteId = userVoteId;
  }

  public RoomUser getRoomUser() {
    return roomUser;
  }

  public void setRoomUser(RoomUser roomUser) {
    this.roomUser = roomUser;
  }

  public RoomOption getRoomOption() {
    return roomOption;
  }

  public void setRoomOption(RoomOption roomOption) {
    this.roomOption = roomOption;
  }

  public VoteType getVoteType() {
    return voteType;
  }

  public void setVoteType(VoteType voteType) {
    this.voteType = voteType;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "UserVote{roomUser=" + this.roomUser +
        ", roomOption=" + this.roomOption +
        ", voteType=" + this.voteType +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  @Override
  public boolean equals(Object userVote) {
    if (userVote == this) {
      return true;
    } else if (!(userVote instanceof UserVote)) {
      return false;
    } else {
      UserVote that = (UserVote) userVote;
      return this.userVoteId != null && this.userVoteId.equals(that.getUserVoteId());
    }
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (this.userVoteId != null ? this.userVoteId.hashCode() : 0);
    return h$;
  }

  public enum VoteType {
    UP, DOWN, NONE
  }
}