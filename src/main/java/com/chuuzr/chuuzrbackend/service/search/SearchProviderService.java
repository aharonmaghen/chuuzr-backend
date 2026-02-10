package com.chuuzr.chuuzrbackend.service.search;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.chuuzr.chuuzrbackend.dto.search.SearchRequestDTO;
import com.chuuzr.chuuzrbackend.dto.search.SearchResponseDTO;
import com.chuuzr.chuuzrbackend.model.Option;
public interface SearchProviderService {
  String getProviderKey();

  SearchResponseDTO search(SearchRequestDTO request, Pageable pageable);

  Map<String, Object> fetchOptionMetadata(String externalId);

  void applyMetadataToOption(Option option, Map<String, Object> metadata);
}
