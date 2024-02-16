package com.movienight.movienightbackend.models;

import java.net.URL;
import java.sql.Date;

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
  private Date updatedAt;
  private Date createdAt;

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
   * @param updatedAt      The last updated date of the user.
   * @param createdAt      The creation date of the user.
   */
  public User(
      Long id,
      String name,
      String nickname,
      String countryCode,
      String phoneNumber,
      URL profilePicture,
      Date updatedAt,
      Date createdAt) {
    this.id = id;
    this.name = name;
    this.nickname = nickname;
    this.countryCode = countryCode;
    this.phoneNumber = phoneNumber;
    this.profilePicture = profilePicture;
    this.updatedAt = updatedAt;
    this.createdAt = createdAt;
  }

  /**
   * Returns the ID of the user.
   *
   * @return The ID of the user.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the user.
   *
   * @param id The ID of the user.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the name of the user.
   *
   * @return The name of the user.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the user.
   *
   * @param name The name of the user.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the nickname of the user.
   *
   * @return The nickname of the user.
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * Sets the nickname of the user.
   *
   * @param nickname The nickname of the user.
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * Returns the country code of the user.
   *
   * @return The country code of the user.
   */
  public String getCountryCode() {
    return countryCode;
  }

  /**
   * Sets the country code of the user.
   *
   * @param countryCode The country code of the user.
   */
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  /**
   * Returns the phone number of the user.
   *
   * @return The phone number of the user.
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the phone number of the user.
   *
   * @param phoneNumber The phone number of the user.
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Returns the profile picture URL of the user.
   *
   * @return The profile picture URL of the user.
   */
  public URL getProfilePicture() {
    return profilePicture;
  }

  /**
   * Sets the profile picture URL of the user.
   *
   * @param profilePicture The profile picture URL of the user.
   */
  public void setProfilePicture(URL profilePicture) {
    this.profilePicture = profilePicture;
  }

  /**
   * Returns the last updated date of the user.
   *
   * @return The last updated date of the user.
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated date of the user.
   *
   * @param updatedAt The last updated date of the user.
   */
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Returns the creation date of the user.
   *
   * @return The creation date of the user.
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation date of the user.
   *
   * @param createdAt The creation date of the user.
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Returns a string representation of the user.
   *
   * @return A string representation of the user.
   */
  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", nickname='" + nickname + '\'' +
        ", countryCode='" + countryCode + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", profilePicture=" + profilePicture +
        ", updatedAt=" + updatedAt +
        ", createdAt=" + createdAt +
        '}';
  }
}