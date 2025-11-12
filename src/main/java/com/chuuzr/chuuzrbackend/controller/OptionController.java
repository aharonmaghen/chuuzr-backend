package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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
  public ResponseEntity<OptionResponseDTO> findById(@PathVariable UUID optionUuid) {
    OptionResponseDTO option = optionService.findByUuid(optionUuid);
    if (option != null) {
      return ResponseEntity.ok(option);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<OptionResponseDTO>> findByOptionTypeUuid(
      @RequestParam UUID optionTypeUuid,
      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    List<OptionResponseDTO> options = optionService.findByOptionTypeUuid(optionTypeUuid, pageable);
    return ResponseEntity.ok(options);
  }

  @PostMapping
  public ResponseEntity<OptionResponseDTO> createOption(@RequestBody OptionRequestDTO newOptionRequest,
      UriComponentsBuilder ucb) {
    OptionResponseDTO createdOption = optionService.createOption(newOptionRequest);
    if (createdOption != null) {
      URI locationOfNewOption = ucb.path("/api/options/{optionUuid}").buildAndExpand(createdOption.getUuid()).toUri();
      return ResponseEntity.created(locationOfNewOption).body(createdOption);
    }
    return ResponseEntity.badRequest().build();
  }

  @PutMapping("/{optionUuid}")
  public ResponseEntity<OptionResponseDTO> updateOption(@PathVariable UUID optionUuid,
      @RequestBody OptionRequestDTO optionToUpdate) {
    OptionResponseDTO updatedOption = optionService.updateOption(optionUuid, optionToUpdate);
    if (updatedOption != null) {
      return ResponseEntity.ok(updatedOption);
    }
    return ResponseEntity.notFound().build();
  }
}
