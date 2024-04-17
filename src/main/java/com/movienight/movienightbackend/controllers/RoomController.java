package com.movienight.movienightbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movienight.movienightbackend.models.Room;
import com.movienight.movienightbackend.repositories.RoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
  @Autowired
  private RoomRepository roomRepository;
  @PersistenceContext
  private EntityManager entityManager;

  @GetMapping
  public List<Room> getAllRooms() {
    return roomRepository.findAll();
  }

  @PostMapping
  @Transactional
  public Room createRoom(@RequestBody Room room) {
    roomRepository.save(room);
    entityManager.refresh(room);
    return room;
  }
}