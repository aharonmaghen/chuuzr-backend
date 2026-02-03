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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.config.OpenApiConfig;
import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeRequestDTO;
import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;
import com.chuuzr.chuuzrbackend.service.OptionTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/option-types")
@Tag(name = "Option Types")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME_NAME)
public class OptionTypeController {
  private static final Logger logger = LoggerFactory.getLogger(OptionTypeController.class);

  private final OptionTypeService optionTypeService;

  @Autowired
  public OptionTypeController(OptionTypeService optionTypeService) {
    this.optionTypeService = optionTypeService;
  }

  @GetMapping("/{optionTypeUuid}")
  @Operation(summary = "Get option type by UUID", description = "Retrieve a specific option type by its unique identifier", operationId = "getOptionTypeById")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Option type found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionTypeResponseDTO.class))),
      @ApiResponse(responseCode = "404", description = "Option type not found", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  })
  public ResponseEntity<OptionTypeResponseDTO> findById(@PathVariable UUID optionTypeUuid) {
    logger.debug("Get option type by UUID request for optionTypeUuid={}", optionTypeUuid);
    OptionTypeResponseDTO optionType = optionTypeService.findByUuid(optionTypeUuid);
    logger.info("Option type retrieved for optionTypeUuid={}", optionTypeUuid);
    return ResponseEntity.ok(optionType);
  }

  @GetMapping
  @Operation(summary = "Get all option types", description = "Retrieve all option types with pagination support", operationId = "getAllOptionTypes")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Option types retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionTypeResponseDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
  })
  public ResponseEntity<List<OptionTypeResponseDTO>> getAllOptionTypes(
      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    logger.debug("Get all option types request received");
    List<OptionTypeResponseDTO> optionTypes = optionTypeService.getAllOptionTypes(pageable);
    logger.info("Option types retrieved, count={}", optionTypes.size());
    return ResponseEntity.ok(optionTypes);
  }

  @PostMapping
  @Operation(summary = "Create a new option type", description = "Create a new option type with the provided information", operationId = "createOptionType")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Option type created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionTypeResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "409", description = "Option type already exists", content = @Content)
  })
  public ResponseEntity<OptionTypeResponseDTO> createOptionType(
      @Valid @RequestBody OptionTypeRequestDTO newOptionTypeRequest,
      UriComponentsBuilder ucb) {
    logger.debug("Create option type request received");
    OptionTypeResponseDTO createdOptionType = optionTypeService.createOptionType(newOptionTypeRequest);
    URI locationOfNewOptionType = ucb.path("/api/option-types/{optionTypeUuid}")
        .buildAndExpand(createdOptionType.getUuid()).toUri();
    logger.info("Option type created with optionTypeUuid={}", createdOptionType.getUuid());
    return ResponseEntity.created(locationOfNewOptionType).body(createdOptionType);
  }

  @PutMapping("/{optionTypeUuid}")
  @Operation(summary = "Update an existing option type", description = "Update option type information for the specified option type UUID", operationId = "updateOptionType")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Option type updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionTypeResponseDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
      @ApiResponse(responseCode = "404", description = "Option type not found", content = @Content)
  })
  public ResponseEntity<OptionTypeResponseDTO> updateOptionType(@PathVariable UUID optionTypeUuid,
      @Valid @RequestBody OptionTypeRequestDTO optionTypeToUpdate) {
    logger.debug("Update option type request for optionTypeUuid={}", optionTypeUuid);
    OptionTypeResponseDTO updatedOptionType = optionTypeService.updateOptionType(optionTypeUuid, optionTypeToUpdate);
    logger.info("Option type updated for optionTypeUuid={}", optionTypeUuid);
    return ResponseEntity.ok(updatedOptionType);
  }
}
