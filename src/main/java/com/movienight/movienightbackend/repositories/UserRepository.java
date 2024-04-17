package com.movienight.movienightbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movienight.movienightbackend.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
}