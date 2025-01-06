package com.movienight.movienightbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.movienight.movienightbackend.models.RoomUser;
import com.movienight.movienightbackend.models.compositeKeys.RoomUserId;

import java.util.Optional;
import java.util.UUID;

public interface RoomUserRepository extends JpaRepository<RoomUser, RoomUserId> {
  Page<RoomUser> findByRoomUuid(UUID roomId, PageRequest pageRequest);
}