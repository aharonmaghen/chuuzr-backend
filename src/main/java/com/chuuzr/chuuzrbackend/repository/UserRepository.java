package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUuid(UUID userUuid);
}