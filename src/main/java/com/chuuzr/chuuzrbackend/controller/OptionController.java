package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.repository.OptionRepository;

@RestController
@RequestMapping("/api/options")
public class OptionController {
  private OptionRepository optionRepository;

  @Autowired
  public OptionController(OptionRepository optionRepository) {
    this.optionRepository = optionRepository;
  }

  @GetMapping("/{optionUuid}")
  public ResponseEntity<Option> findById(@PathVariable UUID optionUuid) {
    Option option = findOption(optionUuid);
    if (option != null) {
      return ResponseEntity.ok(option);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Void> createOption(@RequestBody Option newOptionRequest, UriComponentsBuilder ucb) {
    Option optionToSave = new Option(null, null, newOptionRequest.getOptionTypeId(),
        newOptionRequest.getApiProvider(), newOptionRequest.getExternalId(), newOptionRequest.getName(),
        newOptionRequest.getDescription(), newOptionRequest.getImageUrl(), LocalDateTime.now(), LocalDateTime.now());
    Option savedOption = optionRepository.save(optionToSave);
    URI locationOfNewOption = ucb.path("/api/options/{optionUuid}").buildAndExpand(savedOption.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewOption).build();
  }

  @PutMapping("/{optionUuid}")
  public ResponseEntity<Void> updateOption(@PathVariable UUID optionUuid, @RequestBody Option optionToUpdate) {
    Option option = findOption(optionUuid);
    if (option != null) {
      Option updatedOption = new Option(option.getId(), option.getUuid(), optionToUpdate.getOptionTypeId(),
          optionToUpdate.getApiProvider(), optionToUpdate.getExternalId(), optionToUpdate.getName(),
          optionToUpdate.getDescription(), optionToUpdate.getImageUrl(), LocalDateTime.now(), option.getCreatedAt());
      optionRepository.save(updatedOption);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private Option findOption(UUID optionUuid) {
    return optionRepository.findByUuid(optionUuid).orElse(null);
  }
}
