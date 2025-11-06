package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.model.OptionType;
import com.chuuzr.chuuzrbackend.repository.OptionTypeRepository;

@RestController
@RequestMapping("/api/option-types")
public class OptionTypeController {
  private OptionTypeRepository optionTypeRepository;

  @Autowired
  public OptionTypeController(OptionTypeRepository optionTypeRepository) {
    this.optionTypeRepository = optionTypeRepository;
  }

  @GetMapping("/{optionTypeUuid}")
  public ResponseEntity<OptionType> findById(@PathVariable UUID optionTypeUuid) {
    OptionType optionType = findOptionType(optionTypeUuid);
    if (optionType != null) {
      return ResponseEntity.ok(optionType);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping
  public ResponseEntity<List<OptionType>> getAllOptionTypes(Pageable pageable) {
    Page<OptionType> page = optionTypeRepository.findAll(PageRequest.of(pageable.getPageNumber(),
        pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))));
    return ResponseEntity.ok(page.getContent());
  }

  @PostMapping
  public ResponseEntity<Void> createOptionType(@RequestBody OptionType newOptionTypeRequest, UriComponentsBuilder ucb) {
    OptionType optionTypeToSave = new OptionType(null, null, newOptionTypeRequest.getName(),
        newOptionTypeRequest.getDescription(), LocalDateTime.now(), LocalDateTime.now());
    OptionType savedOptionType = optionTypeRepository.save(optionTypeToSave);
    URI locationOfNewOptionType = ucb.path("/api/option-types/{optionTypeUuid}")
        .buildAndExpand(savedOptionType.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewOptionType).build();
  }

  @PutMapping("/{optionTypeUuid}")
  public ResponseEntity<Void> updateOptionType(@PathVariable UUID optionTypeUuid,
      @RequestBody OptionType optionTypeToUpdate) {
    OptionType optionType = findOptionType(optionTypeUuid);
    if (optionType != null) {
      OptionType updatedOptionType = new OptionType(optionType.getId(), optionType.getUuid(),
          optionTypeToUpdate.getName(), optionTypeToUpdate.getDescription(), LocalDateTime.now(),
          optionType.getCreatedAt());
      optionTypeRepository.save(updatedOptionType);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private OptionType findOptionType(UUID optionTypeUuid) {
    return optionTypeRepository.findByUuid(optionTypeUuid).orElse(null);
  }
}
