package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.compositeKeys.RoomUserId;

import java.util.UUID;

public interface RoomUserRepository extends JpaRepository<RoomUser, RoomUserId> {
  Page<RoomUser> findByRoomUuid(UUID roomId, PageRequest pageRequest);
}