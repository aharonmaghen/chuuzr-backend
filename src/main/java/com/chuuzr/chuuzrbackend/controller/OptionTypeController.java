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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeRequestDTO;
import com.chuuzr.chuuzrbackend.dto.optiontype.OptionTypeResponseDTO;
import com.chuuzr.chuuzrbackend.service.OptionTypeService;

@RestController
@RequestMapping("/api/option-types")
public class OptionTypeController {
  private final OptionTypeService optionTypeService;

  @Autowired
  public OptionTypeController(OptionTypeService optionTypeService) {
    this.optionTypeService = optionTypeService;
  }

  @GetMapping("/{optionTypeUuid}")
  public ResponseEntity<OptionTypeResponseDTO> findById(@PathVariable UUID optionTypeUuid) {
    OptionTypeResponseDTO optionType = optionTypeService.findByUuid(optionTypeUuid);
    if (optionType != null) {
      return ResponseEntity.ok(optionType);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<OptionTypeResponseDTO>> getAllOptionTypes(
      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
    List<OptionTypeResponseDTO> optionTypes = optionTypeService.getAllOptionTypes(pageable);
    return ResponseEntity.ok(optionTypes);
  }

  @PostMapping
  public ResponseEntity<OptionTypeResponseDTO> createOptionType(@RequestBody OptionTypeRequestDTO newOptionTypeRequest,
      UriComponentsBuilder ucb) {
    OptionTypeResponseDTO createdOptionType = optionTypeService.createOptionType(newOptionTypeRequest);
    URI locationOfNewOptionType = ucb.path("/api/option-types/{optionTypeUuid}")
        .buildAndExpand(createdOptionType.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewOptionType).body(createdOptionType);
  }

  @PutMapping("/{optionTypeUuid}")
  public ResponseEntity<OptionTypeResponseDTO> updateOptionType(@PathVariable UUID optionTypeUuid,
      @RequestBody OptionTypeRequestDTO optionTypeToUpdate) {
    OptionTypeResponseDTO updatedOptionType = optionTypeService.updateOptionType(optionTypeUuid, optionTypeToUpdate);
    if (updatedOptionType != null) {
      return ResponseEntity.ok(updatedOptionType);
    }
    return ResponseEntity.notFound().build();
  }
}
