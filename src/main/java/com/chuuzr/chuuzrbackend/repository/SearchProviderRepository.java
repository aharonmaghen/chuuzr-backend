package com.chuuzr.chuuzrbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chuuzr.chuuzrbackend.model.SearchProvider;

public interface SearchProviderRepository extends JpaRepository<SearchProvider, Long> {
  List<SearchProvider> findByOptionTypeIdAndEnabledTrueOrderByIdAsc(Long optionTypeId);

  boolean existsByOptionTypeIdAndProviderKeyIgnoreCaseAndEnabledTrue(Long optionTypeId, String providerKey);
}
