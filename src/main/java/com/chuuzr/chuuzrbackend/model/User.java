package com.chuuzr.chuuzrbackend.model;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid;

  private String name;
  private String nickname;
  private String countryCode;
  private String phoneNumber;
  private URL profilePicture;

  @JsonIgnore
  @Column(name = "otp_code")
  private String otpCode;

  @JsonIgnore
  @Column(name = "otp_expiration_time")
  private LocalDateTime otpExpirationTime;

  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  public User() {
  }

  public User(
      Long id,
      UUID uuid,
      String name,
      String nickname,
      String countryCode,
      String phoneNumber,
      URL profilePicture,
      String otpCode,
      LocalDateTime otpExpirationTime,
      LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.nickname = nickname;
    this.countryCode = countryCode;
    this.phoneNumber = phoneNumber;
    this.profilePicture = profilePicture;
    this.otpCode = otpCode;
    this.otpExpirationTime = otpExpirationTime;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  public User(
      Long id,
      UUID uuid,
      String name,
      String nickname,
      String countryCode,
      String phoneNumber,
      URL profilePicture,
      LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this(id, uuid, name, nickname, countryCode, phoneNumber, profilePicture, null, null, updatedAt, createdAt);
  }

  @PrePersist
  private void prePersist() {
    if (uuid == null) {
      uuid = UUID.randomUUID();
    }
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public URL getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(URL profilePicture) {
    this.profilePicture = profilePicture;
  }

  public String getOtpCode() {
    return otpCode;
  }

  public void setOtpCode(String otpCode) {
    this.otpCode = otpCode;
  }

  public LocalDateTime getOtpExpirationTime() {
    return otpExpirationTime;
  }

  public void setOtpExpirationTime(LocalDateTime otpExpirationTime) {
    this.otpExpirationTime = otpExpirationTime;
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
    return "User{id=" + this.id +
        ", uuid=" + this.uuid +
        ", name=" + this.name +
        ", nickname=" + this.nickname +
        ", countryCode=" + this.countryCode +
        ", phoneNumber=" + this.phoneNumber +
        ", profilePicture=" + this.profilePicture +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  @Override
  public boolean equals(Object user) {
    if (user == this) {
      return true;
    } else if (!(user instanceof User)) {
      return false;
    } else {
      User that = (User) user;
      return this.id != null && this.id.equals(that.getId());
    }
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (this.uuid != null ? this.uuid.hashCode() : 0);
    return h$;
  }
}
