package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.model.compositekeys.RoomOptionId;

import java.util.Optional;
import java.util.UUID;

public interface RoomOptionRepository extends JpaRepository<RoomOption, RoomOptionId> {
  Page<RoomOption> findByRoomUuid(UUID roomId, Pageable pageable);

  @Query("SELECT ro FROM RoomOption ro WHERE ro.room.uuid = :roomUuid AND ro.option.uuid = :optionUuid")
  Optional<RoomOption> findByUuids(UUID roomUuid, UUID optionUuid);

  @Modifying
  @Query("UPDATE RoomOption ro SET ro.score = ro.score + 1 " +
      "WHERE ro.roomOptionId.roomId = :roomId AND ro.roomOptionId.optionId = :optionId")
  void incrementScore(@Param("roomId") Long roomId, @Param("optionId") Long optionId);

  @Modifying
  @Query("UPDATE RoomOption ro SET ro.score = ro.score - 1 " +
      "WHERE ro.roomOptionId.roomId = :roomId AND ro.roomOptionId.optionId = :optionId")
  void decrementScore(@Param("roomId") Long roomId, @Param("optionId") Long optionId);
}