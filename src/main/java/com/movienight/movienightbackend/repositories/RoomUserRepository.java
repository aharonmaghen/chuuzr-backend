package com.movienight.movienightbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movienight.movienightbackend.models.RoomUser;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
  
}