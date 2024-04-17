package com.movienight.movienightbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movienight.movienightbackend.models.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
  
}