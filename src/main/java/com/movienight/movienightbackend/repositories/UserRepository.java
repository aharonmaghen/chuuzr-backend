package com.movienight.movienightbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movienight.movienightbackend.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUuid(UUID userUuid);
}