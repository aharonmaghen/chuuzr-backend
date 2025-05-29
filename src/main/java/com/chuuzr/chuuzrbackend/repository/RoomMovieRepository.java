package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.RoomMovie;

public interface RoomMovieRepository extends JpaRepository<RoomMovie, Long> {
  
}