package com.chuuzr.chuuzrbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.OptionType;

import java.util.Optional;
import java.util.UUID;

public interface OptionTypeRepository extends JpaRepository<OptionType, Long> {
  Optional<OptionType> findByUuid(UUID uuid);
}
