package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.option.OptionRequestDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionResponseDTO;
import com.chuuzr.chuuzrbackend.service.OptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/options")
@Tag(name = "Options")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class OptionController {
  private final OptionService optionService;

  @Autowired
  public OptionController(OptionService optionService) {
    this.optionService = optionService;
  }

  @GetMapping("/{optionUuid}")
  @Operation(summary = "Get option by UUID", description = "Retrieve a specific option by its unique identifier", operationId = "getOptionById")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Option found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Option not found", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  })
  public ResponseEntity<OptionResponseDTO> findById(@PathVariable UUID optionUuid) {
    OptionResponseDTO option = optionService.findByUuid(optionUuid);
    return ResponseEntity.ok(option);
  }

  @GetMapping
  @Operation(summary = "Get options by option type", description = "Retrieve all options for a specific option type with pagination support", operationId = "getOptionsByOptionType")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Options retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  })
  public ResponseEntity<List<OptionResponseDTO>> findByOptionTypeUuid(
      @RequestParam UUID optionTypeUuid,
      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    List<OptionResponseDTO> options = optionService.findByOptionTypeUuid(optionTypeUuid, pageable);
    return ResponseEntity.ok(options);
  }

  @PostMapping
  @Operation(summary = "Create a new option", description = "Create a new option with the provided information", operationId = "createOption")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Option created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "409", description = "Option already exists", content = @Content)
  })
  public ResponseEntity<OptionResponseDTO> createOption(@Valid @RequestBody OptionRequestDTO newOptionRequest,
      UriComponentsBuilder ucb) {
    OptionResponseDTO createdOption = optionService.createOption(newOptionRequest);
    URI locationOfNewOption = ucb.path("/api/options/{optionUuid}").buildAndExpand(createdOption.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewOption).body(createdOption);
  }

  @PutMapping("/{optionUuid}")
  @Operation(summary = "Update an existing option", description = "Update option information for the specified option UUID", operationId = "updateOption")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Option updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "404", description = "Option not found", content = @Content)
  })
  public ResponseEntity<OptionResponseDTO> updateOption(@PathVariable UUID optionUuid,
      @Valid @RequestBody OptionRequestDTO optionToUpdate) {
    OptionResponseDTO updatedOption = optionService.updateOption(optionUuid, optionToUpdate);
    return ResponseEntity.ok(updatedOption);
  }
}
