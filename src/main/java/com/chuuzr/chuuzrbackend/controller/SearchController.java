package com.chuuzr.chuuzrbackend.controller;

import java.util.UUID;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.error.ErrorDTO;
import com.chuuzr.chuuzrbackend.dto.search.SearchRequestDTO;
import com.chuuzr.chuuzrbackend.dto.search.SearchResponseDTO;
import com.chuuzr.chuuzrbackend.service.search.SearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Search")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class SearchController {
  private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

  private final SearchService searchService;

  @Autowired
  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }

  @PostMapping("/{roomUuid}/search")
  @Operation(summary = "Search options for a room", description = "Search external providers for options based on the room's option type", operationId = "search")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Search completed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<SearchResponseDTO> search(@PathVariable UUID roomUuid,
      @Valid @RequestBody SearchRequestDTO searchRequest, Pageable pageable) {

    logger.debug("Search request received for roomUuid={}", roomUuid);
    SearchResponseDTO response = searchService.search(roomUuid, searchRequest, pageable);
    logger.info("Search response returned for roomUuid={}", roomUuid);

    return ResponseEntity.ok(response);
  }
}
