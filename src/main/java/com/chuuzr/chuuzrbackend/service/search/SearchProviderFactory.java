package com.chuuzr.chuuzrbackend.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ValidationException;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.SearchProvider;
import com.chuuzr.chuuzrbackend.repository.SearchProviderRepository;

@Component
public class SearchProviderFactory {
  private final Map<String, SearchProviderService> providerMap;
  private final SearchProviderRepository searchProviderRepository;

  @Autowired
  public SearchProviderFactory(List<SearchProviderService> providers,
      SearchProviderRepository searchProviderRepository) {
    this.providerMap = providers.stream()
        .collect(Collectors.toMap(provider -> normalizeKey(provider.getProviderKey()), Function.identity()));
    this.searchProviderRepository = searchProviderRepository;
  }

  public List<SearchProviderService> getProvidersForRoom(Room room) {
    if (room == null || room.getOptionType() == null || room.getOptionType().getUuid() == null) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Room option type is required");
    }

    Long optionTypeId = room.getOptionType().getId();
    if (optionTypeId == null) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Room option type id is required");
    }

    List<SearchProvider> configs = searchProviderRepository
        .findByOptionTypeIdAndEnabledTrueOrderByIdAsc(optionTypeId);
    if (configs.isEmpty()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT,
          "No search provider configured for option type " + room.getOptionType().getUuid());
    }

    List<SearchProviderService> providers = new ArrayList<>();
    for (SearchProvider config : configs) {
      String providerKey = normalizeKey(config.getProviderKey());
      if (providerKey.isBlank()) {
        throw new ValidationException(ErrorCode.INVALID_INPUT, "Search provider key is required");
      }
      SearchProviderService provider = providerMap.get(providerKey);
      if (provider == null) {
        throw new ValidationException(ErrorCode.INVALID_INPUT,
            "No search provider registered for key " + config.getProviderKey());
      }
      providers.add(provider);
    }

    return providers;
  }

  public SearchProviderService getProviderByKey(String providerKey) {
    String normalizedKey = normalizeKey(providerKey);
    if (normalizedKey.isBlank()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT, "Provider key is required");
    }

    SearchProviderService provider = providerMap.get(normalizedKey);
    if (provider == null) {
      throw new ValidationException(ErrorCode.INVALID_INPUT,
          "No search provider registered for key " + providerKey);
    }

    return provider;
  }

  private String normalizeKey(String value) {
    return value == null ? "" : value.trim().toUpperCase();
  }

}
