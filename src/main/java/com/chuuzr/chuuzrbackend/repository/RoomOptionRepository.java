package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.model.compositekeys.RoomOptionId;

import java.util.UUID;

public interface RoomOptionRepository extends JpaRepository<RoomOption, RoomOptionId> {
  Page<RoomOption> findByRoomUuid(UUID roomId, Pageable pageable);
}