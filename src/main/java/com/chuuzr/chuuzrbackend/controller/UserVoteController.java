package com.chuuzr.chuuzrbackend.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.dto.uservote.UserVoteRequestDTO;
import com.chuuzr.chuuzrbackend.dto.uservote.UserVoteResponseDTO;
import com.chuuzr.chuuzrbackend.service.UserVoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Rooms")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class UserVoteController {
  private static final Logger logger = LoggerFactory.getLogger(UserVoteController.class);

  private final UserVoteService userVoteService;

  @Autowired
  public UserVoteController(UserVoteService userVoteService) {
    this.userVoteService = userVoteService;
  }

  @PutMapping("/{roomUuid}/options/{optionUuid}/votes")
  @Operation(summary = "Cast or update a vote", description = "Create or update the authenticated user's vote for an option in a room. All transitions (UP, DOWN, NONE) are allowed directly.", operationId = "castUserVote")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Vote recorded successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserVoteResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input or invalid vote transition", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "404", description = "Room, option, or user not found", content = @Content)
  })
  public ResponseEntity<UserVoteResponseDTO> castVote(@PathVariable("roomUuid") UUID roomUuid,
      @PathVariable("optionUuid") UUID optionUuid, @Valid @RequestBody UserVoteRequestDTO userVoteRequestDTO,
      Authentication authentication) {
    UserInternalDTO userContext = (UserInternalDTO) authentication.getPrincipal();
    logger.debug("Cast vote request for roomUuid={}, optionUuid={}, userUuid={}", roomUuid, optionUuid,
        userContext.getUuid());
    UserVoteResponseDTO voteResponse = userVoteService.castVote(roomUuid, userContext.getUuid(), optionUuid,
        userVoteRequestDTO.getVoteType());
    logger.info("Vote recorded for roomUuid={}, optionUuid={}, userUuid={}, voteType={}", roomUuid, optionUuid,
        userContext.getUuid(), userVoteRequestDTO.getVoteType());
    return ResponseEntity.ok(voteResponse);
  }
}
