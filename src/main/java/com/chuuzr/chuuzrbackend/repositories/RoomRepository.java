package com.chuuzr.chuuzrbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.models.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, Long> {
  Optional<Room> findByUuid(UUID roomUuid);
}