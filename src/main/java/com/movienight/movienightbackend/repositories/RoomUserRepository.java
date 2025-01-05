package com.movienight.movienightbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.movienight.movienightbackend.models.RoomUser;
import com.movienight.movienightbackend.models.compositeKeys.RoomUserId;

public interface RoomUserRepository extends JpaRepository<RoomUser, RoomUserId> {
  Page<RoomUser> findByRoomId(Long roomId, PageRequest pageRequest);
}