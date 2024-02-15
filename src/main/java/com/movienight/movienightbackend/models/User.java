package com.movienight.movienightbackend.models;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;

import com.google.auto.value.AutoValue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a user.
 */
@AutoValue
@Entity
public abstract class User {
  /**
   * Returns the ID of the user.
   *
   * @return The ID of the user.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public abstract Long id();

  /**
   * Returns the name of the user.
   *
   * @return The name of the user.
   */
  public abstract String name();

  /**
   * Returns the nickname of the user.
   *
   * @return The nickname of the user.
   */
  public abstract String nickname();

  /**
   * Returns the country code of the user.
   *
   * @return The country code of the user.
   */
  public abstract String countryCode();

  /**
   * Returns the phone number of the user.
   *
   * @return The phone number of the user.
   */
  public abstract String phoneNumber();

  /**
   * Returns the profile picture URL of the user.
   *
   * @return The profile picture URL of the user.
   */
  public abstract URL profilePicture();

  /**
   * Returns the optional last updated date of the user.
   *
   * @return The optional last updated date of the user.
   */
  public abstract Optional<Date> updatedAt();

  /**
   * Returns the optional creation date of the user.
   *
   * @return The optional creation date of the user.
   */
  public abstract Optional<Date> createdAt();

  /**
   * Creates a new User instance.
   *
   * @param id             The ID of the user.
   * @param name           The name of the user.
   * @param nickname       The nickname of the user.
   * @param countryCode    The country code of the user.
   * @param phoneNumber    The phone number of the user.
   * @param profilePicture The profile picture URL of the user.
   * @param updatedAt      The optional last updated date of the user.
   * @param createdAt      The optional creation date of the user.
   * @return A new User instance.
   */
  public static User create(
      Long id,
      String name,
      String nickname,
      String countryCode,
      String phoneNumber,
      URL profilePicture,
      Optional<Date> updatedAt,
      Optional<Date> createdAt) {
    return new AutoValue_User(id, name, nickname, countryCode, phoneNumber, profilePicture, updatedAt, createdAt);
  }
}