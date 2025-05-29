package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, Long> {
  Optional<Room> findByUuid(UUID roomUuid);
}