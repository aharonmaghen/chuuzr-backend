package com.movienight.movienightbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movienight.movienightbackend.models.RoomMovie;

public interface RoomMovieRepository extends JpaRepository<RoomMovie, Long> {
  
}