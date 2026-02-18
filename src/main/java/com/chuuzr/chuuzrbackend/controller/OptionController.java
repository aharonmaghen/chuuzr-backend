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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.error.ErrorDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionDetailResponseDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionRequestDTO;
import com.chuuzr.chuuzrbackend.dto.option.OptionSummaryResponseDTO;
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
  private static final Logger logger = LoggerFactory.getLogger(OptionController.class);

  private final OptionService optionService;

  @Autowired
  public OptionController(OptionService optionService) {
    this.optionService = optionService;
  }

  @GetMapping("/{optionUuid}")
  @Operation(summary = "Get option by UUID", description = "Retrieve a specific option by its unique identifier", operationId = "getOptionById")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Option found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionDetailResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Option not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<OptionDetailResponseDTO> findById(@PathVariable UUID optionUuid) {
    logger.debug("Get option by UUID request for optionUuid={}", optionUuid);
    OptionDetailResponseDTO option = optionService.findByUuid(optionUuid);
    logger.info("Option retrieved for optionUuid={}", optionUuid);
    return ResponseEntity.ok(option);
  }

  @GetMapping
  @Operation(summary = "Get options by option type", description = "Retrieve all options for a specific option type with pagination support", operationId = "getOptionsByOptionType")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Options retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionSummaryResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<List<OptionSummaryResponseDTO>> findByOptionTypeUuid(
      @RequestParam UUID optionTypeUuid,
      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    logger.debug("Get options by option type request for optionTypeUuid={}", optionTypeUuid);
    List<OptionSummaryResponseDTO> options = optionService.findByOptionTypeUuid(optionTypeUuid, pageable);
    logger.info("Options retrieved for optionTypeUuid={}, count={}", optionTypeUuid, options.size());
    return ResponseEntity.ok(options);
  }

  @PostMapping
  @Operation(summary = "Create a new option", description = "Create a new option with the provided information", operationId = "createOption")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Option created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionDetailResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "409", description = "Option already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<OptionDetailResponseDTO> createOption(@Valid @RequestBody OptionRequestDTO newOptionRequest,
      UriComponentsBuilder ucb) {
    logger.debug("Create option request received");
    OptionDetailResponseDTO createdOption = optionService.createOption(newOptionRequest);
    URI locationOfNewOption = ucb.path("/api/options/{optionUuid}").buildAndExpand(createdOption.getUuid()).toUri();
    logger.info("Option created with optionUuid={}", createdOption.getUuid());
    return ResponseEntity.created(locationOfNewOption).body(createdOption);
  }

  @PutMapping("/{optionUuid}")
  @Operation(summary = "Update an existing option", description = "Update option information for the specified option UUID", operationId = "updateOption")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Option updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionDetailResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Option not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
  })
  public ResponseEntity<OptionDetailResponseDTO> updateOption(@PathVariable UUID optionUuid,
      @Valid @RequestBody OptionRequestDTO optionToUpdate) {
    logger.debug("Update option request for optionUuid={}", optionUuid);
    OptionDetailResponseDTO updatedOption = optionService.updateOption(optionUuid, optionToUpdate);
    logger.info("Option updated for optionUuid={}", optionUuid);
    return ResponseEntity.ok(updatedOption);
  }
}
