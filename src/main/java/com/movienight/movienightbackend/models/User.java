package com.movienight.movienightbackend.models;

import java.net.URL;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a user.
 */
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String nickname;
  private String countryCode;
  private String phoneNumber;
  private URL profilePicture;
  private LocalDateTime updatedAt;
  private LocalDateTime createdAt;

  /**
   * Default constructor for User required by JPA.
   */
  public User() {
  }

  /**
   * Constructs a new User with the specified attributes.
   *
   * @param id             The ID of the user.
   * @param name           The name of the user.
   * @param nickname       The nickname of the user.
   * @param countryCode    The country code of the user.
   * @param phoneNumber    The phone number of the user.
   * @param profilePicture The profile picture URL of the user.
   * @param updatedAt      The last updated timestamp of the user.
   * @param createdAt      The creation timestamp of the user.
   */
  public User(
      Long id,
      String name,
      String nickname,
      String countryCode,
      String phoneNumber,
      URL profilePicture,
      LocalDateTime updatedAt,
      LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.nickname = nickname;
    this.countryCode = countryCode;
    this.phoneNumber = phoneNumber;
    this.profilePicture = profilePicture;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String toString() {
    return "User{id=" + this.id +
        ", name=" + this.name +
        ", nickname=" + this.nickname +
        ", countryCode=" + this.countryCode +
        ", phoneNumber=" + this.phoneNumber +
        ", profilePicture=" + this.profilePicture +
        ", updatedAt=" + this.updatedAt +
        ", createdAt=" + this.createdAt + "}";
  }

  public boolean equals(Object user) {
    if (user == this) {
      return true;
    } else if (!(user instanceof User)) {
      return false;
    } else {
      User that = (User) user;
      return this.id.equals(that.getId()) &&
          this.name.equals(that.getName()) &&
          this.nickname.equals(that.getNickname()) &&
          this.countryCode.equals(that.getCountryCode()) &&
          this.phoneNumber.equals(that.getPhoneNumber()) &&
          this.profilePicture.equals(that.getProfilePicture()) &&
          this.updatedAt.equals(that.getUpdatedAt()) &&
          this.createdAt.equals(that.getCreatedAt());
    }
  }

  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= this.id.hashCode();
    h$ *= 1000003;
    h$ ^= this.name.hashCode();
    h$ *= 1000003;
    h$ ^= this.nickname.hashCode();
    h$ *= 1000003;
    h$ ^= this.countryCode.hashCode();
    h$ *= 1000003;
    h$ ^= this.phoneNumber.hashCode();
    h$ *= 1000003;
    h$ ^= this.profilePicture.hashCode();
    h$ *= 1000003;
    h$ ^= this.updatedAt.hashCode();
    h$ *= 1000003;
    h$ ^= this.createdAt.hashCode();
    return h$;
  }
}