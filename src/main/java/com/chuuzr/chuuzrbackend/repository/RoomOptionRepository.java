package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.model.compositeKeys.RoomOptionId;

import java.util.UUID;

public interface RoomOptionRepository extends JpaRepository<RoomOption, RoomOptionId> {
  Page<RoomOption> findByRoomUuid(UUID roomId, PageRequest pageRequest);
}