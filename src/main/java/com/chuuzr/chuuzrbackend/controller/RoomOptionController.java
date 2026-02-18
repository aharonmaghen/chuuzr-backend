package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.error.ErrorDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionSummaryResponseDTO;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionRequestDTO;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionResponseDTO;
import com.chuuzr.chuuzrbackend.service.RoomOptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Rooms")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class RoomOptionController {
  private static final Logger logger = LoggerFactory.getLogger(RoomOptionController.class);

  private final RoomOptionService roomOptionService;

  @Autowired
  public RoomOptionController(RoomOptionService roomOptionService) {
    this.roomOptionService = roomOptionService;
  }

  @GetMapping("/{roomUuid}/options")
  @Operation(summary = "Get options for a room", description = "Retrieve all options that are associated with a specific room with pagination support", operationId = "getRoomOptions")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Options retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionSummaryResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<List<OptionSummaryResponseDTO>> getRoomOptions(@PathVariable UUID roomUuid,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
    logger.debug("Get room options request for roomUuid={}", roomUuid);
    List<OptionSummaryResponseDTO> options = roomOptionService.getRoomOptions(roomUuid, pageable);
    logger.info("Room options retrieved for roomUuid={}, count={}", roomUuid, options.size());
    return ResponseEntity.ok(options);
  }

  @PostMapping("/{roomUuid}/options")
  @Operation(summary = "Add option to room", description = "Add an option to a specific room", operationId = "addOptionToRoom")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Option added to room successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomOptionResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Room or option not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "409", description = "Option already in room", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<RoomOptionResponseDTO> addOptionToRoom(@PathVariable UUID roomUuid,
      @Valid @RequestBody RoomOptionRequestDTO roomOptionRequest, UriComponentsBuilder ucb) {
    logger.debug("Add option to room request for roomUuid={}, optionUuid={}", roomUuid,
        roomOptionRequest.getOptionUuid());
    RoomOptionResponseDTO addedRoomOption = roomOptionService.addOptionToRoom(roomUuid,
        roomOptionRequest.getOptionUuid());
    URI locationOfNewOption = ucb.path("/api/rooms/{roomUuid}/options")
        .buildAndExpand(addedRoomOption.getRoom().getUuid()).toUri();
    logger.info("Option added to room for roomUuid={}, optionUuid={}", roomUuid,
        roomOptionRequest.getOptionUuid());
    return ResponseEntity.created(locationOfNewOption).body(addedRoomOption);
  }
}
