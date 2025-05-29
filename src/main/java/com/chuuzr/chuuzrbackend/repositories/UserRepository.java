package com.chuuzr.chuuzrbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUuid(UUID userUuid);
}