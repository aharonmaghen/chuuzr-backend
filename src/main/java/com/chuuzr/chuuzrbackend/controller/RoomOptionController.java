package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.repository.OptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomOptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;

/**
 * REST controller for managing room-option relationships.
 */
@RestController
@RequestMapping("/api/room-options")
public class RoomOptionController {
  private RoomOptionRepository roomOptionRepository;
  private RoomRepository roomRepository;
  private OptionRepository optionRepository;

  @Autowired
  public RoomOptionController(RoomOptionRepository roomOptionRepository, RoomRepository roomRepository,
      OptionRepository optionRepository) {
    this.roomOptionRepository = roomOptionRepository;
    this.roomRepository = roomRepository;
    this.optionRepository = optionRepository;
  }

  /**
   * Get all options for a specific room.
   *
   * @param roomUuid The UUID of the room
   * @param pageable Pagination parameters
   * @return List of options in the room
   */
  @GetMapping("/{roomUuid}/options")
  public ResponseEntity<List<Option>> getRoomOptions(@PathVariable UUID roomUuid, Pageable pageable) {
    Page<Option> page = roomOptionRepository.findByRoomUuid(roomUuid, PageRequest.of(pageable.getPageNumber(),
        pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "optionId"))))
        .map(RoomOption::getOption);
    return ResponseEntity.ok(page.getContent());
  }

  /**
   * Add an option to a room.
   *
   * @param roomUuid    The UUID of the room
   * @param requestBody Request body containing optionUuid
   * @param ucb         URI components builder
   * @return Response with location of created resource
   */
  @PostMapping("/{roomUuid}/add-option")
  public ResponseEntity<Void> addOptionToRoom(@PathVariable UUID roomUuid, @RequestBody Map<String, Object> roomOption,
      UriComponentsBuilder ucb) {
    Room room = roomRepository.findByUuid(roomUuid).orElse(null);
    Option option = optionRepository.findByUuid(UUID.fromString(roomOption.get("optionUuid").toString())).orElse(null);

    if (room != null && option != null) {
      RoomOption roomOptionToAdd = new RoomOption(null, room, option, LocalDateTime.now(), LocalDateTime.now());
      RoomOption addedRoomOption = roomOptionRepository.save(roomOptionToAdd);
      URI locationOfNewOption = ucb.path("/api/room-options/{roomUuid}/options")
          .buildAndExpand(addedRoomOption.getRoom().getUuid()).toUri();
      return ResponseEntity.created(locationOfNewOption).build();
    }

    return ResponseEntity.notFound().build();
  }
}
