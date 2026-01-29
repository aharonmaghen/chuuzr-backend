package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.compositekeys.RoomUserId;

import java.util.Optional;
import java.util.UUID;

public interface RoomUserRepository extends JpaRepository<RoomUser, RoomUserId> {
  Page<RoomUser> findByRoomUuid(UUID roomUuid, Pageable pageable);

  @Query("SELECT ru FROM RoomUser ru WHERE ru.room.uuid = :roomUuid AND ru.user.uuid = :userUuid")
  Optional<RoomUser> findByUuids(UUID roomUuid, UUID userUuid);
}