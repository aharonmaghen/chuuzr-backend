package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.dto.option.OptionResponseDTO;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionRequestDTO;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionResponseDTO;
import com.chuuzr.chuuzrbackend.service.RoomOptionService;

/**
 * REST controller for managing room-option relationships.
 */
@RestController
@RequestMapping("/api/room-options")
public class RoomOptionController {
  private final RoomOptionService roomOptionService;

  @Autowired
  public RoomOptionController(RoomOptionService roomOptionService) {
    this.roomOptionService = roomOptionService;
  }

  /**
   * Get all options for a specific room.
   *
   * @param roomUuid The UUID of the room
   * @param pageable Pagination parameters
   * @return List of options in the room
   */
  @GetMapping("/{roomUuid}/options")
  public ResponseEntity<List<OptionResponseDTO>> getRoomOptions(@PathVariable UUID roomUuid,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
    List<OptionResponseDTO> options = roomOptionService.getRoomOptions(roomUuid, pageable);
    return ResponseEntity.ok(options);
  }

  /**
   * Add an option to a room.
   *
   * @param roomUuid          The UUID of the room
   * @param roomOptionRequest Request body containing optionUuid
   * @param ucb               URI components builder
   * @return Response with location of created resource
   */
  @PostMapping("/{roomUuid}/add-option")
  public ResponseEntity<RoomOptionResponseDTO> addOptionToRoom(@PathVariable UUID roomUuid,
      @RequestBody RoomOptionRequestDTO roomOptionRequest, UriComponentsBuilder ucb) {
    RoomOptionResponseDTO addedRoomOption = roomOptionService.addOptionToRoom(roomUuid,
        roomOptionRequest.getOptionUuid());

    if (addedRoomOption != null) {
      URI locationOfNewOption = ucb.path("/api/room-options/{roomUuid}/options")
          .buildAndExpand(addedRoomOption.getRoom().getUuid()).toUri();
      return ResponseEntity.created(locationOfNewOption).body(addedRoomOption);
    }

    return ResponseEntity.notFound().build();
  }
}
