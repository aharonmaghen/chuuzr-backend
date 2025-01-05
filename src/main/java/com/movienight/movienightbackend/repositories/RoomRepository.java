package com.movienight.movienightbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movienight.movienightbackend.models.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, Long> {
  Optional<Room> findByUuid(UUID roomUuid);
}