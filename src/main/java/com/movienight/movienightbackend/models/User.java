package com.movienight.movienightbackend.models;

import java.sql.Date;

import com.google.auto.value.AutoValue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@AutoValue
@Entity
public abstract class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public abstract Long id();
  public abstract String name();
  public abstract String nickname();
  public abstract String countryCode();
  public abstract String phoneNumber();
  public abstract String profilePicture();
  public abstract Date updatedAt();
  public abstract Date createdAt();

  public static User create(Long id, String name, String nickname, String countryCode, String phoneNumber, String profilePicture, Date updatedAt, Date createdAt) {
    return new AutoValue_User(id, name, nickname, countryCode, phoneNumber, profilePicture, updatedAt, createdAt);
  }
}
