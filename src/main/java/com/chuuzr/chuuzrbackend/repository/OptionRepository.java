package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.Option;

import java.util.Optional;
import java.util.UUID;

public interface OptionRepository extends JpaRepository<Option, Long> {
  Optional<Option> findByUuid(UUID uuid);

  Page<Option> findByOptionTypeUuid(UUID optionTypeUuid, Pageable pageable);
}
