package com.chuuzr.chuuzrbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.models.RoomMovie;

public interface RoomMovieRepository extends JpaRepository<RoomMovie, Long> {
  
}