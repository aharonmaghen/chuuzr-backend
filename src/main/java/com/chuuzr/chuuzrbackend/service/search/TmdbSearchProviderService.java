package com.chuuzr.chuuzrbackend.service.search;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.dto.search.SearchRequestDTO;
import com.chuuzr.chuuzrbackend.dto.search.SearchResponseDTO;
import com.chuuzr.chuuzrbackend.dto.search.SearchResultDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ExternalApiException;
import com.chuuzr.chuuzrbackend.exception.SearchProviderException;
import com.chuuzr.chuuzrbackend.model.Option;

@Service
public class TmdbSearchProviderService implements SearchProviderService {
  private static final Logger logger = LoggerFactory.getLogger(TmdbSearchProviderService.class);
  private static final String API_PROVIDER = "TMDB";
  private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
  private static final String SEARCH_PATH = "/search/movie";
  private static final String DETAILS_PATH = "/movie/{externalId}";

  private final RestTemplate restTemplate;
  private final String tmdbBaseUrl;
  private final String tmdbApiKey;

  @Autowired
  public TmdbSearchProviderService(RestTemplateBuilder restTemplateBuilder,
      @Value("${tmdb.base-url}") String tmdbBaseUrl,
      @Value("${tmdb.api-key}") String tmdbApiKey) {
    this.restTemplate = restTemplateBuilder.build();
    this.tmdbBaseUrl = tmdbBaseUrl;
    this.tmdbApiKey = tmdbApiKey;
  }

  @Override
  public String getProviderKey() {
    return API_PROVIDER;
  }

  @Override
  public SearchResponseDTO search(SearchRequestDTO request, Pageable pageable) {
    validateConfig();
    return executeTmdbCall("search", request.getQuery(), () -> {
      int pageNumber = pageable.getPageNumber() + 1;
      UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl)
          .path(SEARCH_PATH)
          .queryParam("api_key", tmdbApiKey)
          .queryParam("query", request.getQuery())
          .queryParam("page", pageNumber);

      String uri = uriBuilder.build().encode().toUriString();
      TmdbSearchResponse response = restTemplate.getForObject(uri, TmdbSearchResponse.class);

      if (response == null || response.getResults() == null) {
        return new SearchResponseDTO(Collections.emptyList());
      }

      int pageSize = pageable.getPageSize();
      List<SearchResultDTO> results = response.getResults().stream()
          .limit(pageSize)
          .map(this::toSearchResult)
          .collect(Collectors.toList());

      return new SearchResponseDTO(results);
    });
  }

  @Override
  public Map<String, Object> fetchOptionMetadata(String externalId) {
    validateConfig();
    if (externalId == null || externalId.isBlank()) {
      throw new SearchProviderException(ErrorCode.INVALID_INPUT, "External ID is required");
    }

    return executeTmdbCall("details", externalId, () -> {
      UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(tmdbBaseUrl)
          .path(DETAILS_PATH)
          .queryParam("api_key", tmdbApiKey);

      String uri = uriBuilder.buildAndExpand(externalId).encode().toUriString();
      @SuppressWarnings("unchecked")
      Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
      return response == null ? Collections.emptyMap() : response;
    });
  }

  @Override
  public void applyMetadataToOption(Option option, Map<String, Object> metadata) {
    if (option == null || metadata == null || metadata.isEmpty()) {
      return;
    }

    String title = getString(metadata, "title");
    if (isBlank(option.getName()) && !isBlank(title)) {
      option.setName(title);
    }

    String overview = getString(metadata, "overview");
    if (isBlank(option.getDescription()) && !isBlank(overview)) {
      option.setDescription(overview);
    }

    String posterPath = getString(metadata, "poster_path");
    if (isBlank(option.getImageUrl()) && !isBlank(posterPath)) {
      option.setImageUrl(IMAGE_BASE_URL + posterPath);
    }
  }

  private SearchResultDTO toSearchResult(TmdbSearchResult result) {
    String imageUrl = result.getPosterPath() != null ? IMAGE_BASE_URL + result.getPosterPath() : null;
    return new SearchResultDTO(API_PROVIDER, String.valueOf(result.getId()), result.getTitle(),
        result.getOverview(), imageUrl);
  }

  private void validateConfig() {
    if (tmdbBaseUrl == null || tmdbBaseUrl.isBlank() || tmdbApiKey == null || tmdbApiKey.isBlank()) {
      throw new SearchProviderException(ErrorCode.SEARCH_PROVIDER_CONFIG_ERROR,
          "TMDB configuration is missing or invalid");
    }
  }

  private <T> T executeTmdbCall(String context, String identifier, Supplier<T> action) {
    try {
      return action.get();
    } catch (HttpClientErrorException ex) {
      logger.error("TMDB {} client error for identifier={}, status={}", context, identifier, ex.getStatusCode(), ex);
      if (ex.getStatusCode().value() == 401 || ex.getStatusCode().value() == 403) {
        throw new ExternalApiException(ErrorCode.EXTERNAL_API_AUTH_FAILED, "TMDB authentication failed", ex);
      }
      throw new ExternalApiException(ErrorCode.EXTERNAL_API_BAD_RESPONSE,
          "TMDB rejected the request", ex);
    } catch (HttpServerErrorException ex) {
      logger.error("TMDB {} server error for identifier={}, status={}", context, identifier, ex.getStatusCode(), ex);
      throw new ExternalApiException(ErrorCode.EXTERNAL_API_BAD_RESPONSE,
          "TMDB returned an error", ex);
    } catch (ResourceAccessException ex) {
      logger.error("TMDB unavailable for identifier={}, context={}", identifier, context, ex);
      throw new ExternalApiException(ErrorCode.EXTERNAL_API_UNAVAILABLE,
          "TMDB is unavailable", ex);
    } catch (RestClientException ex) {
      logger.error("TMDB {} request failed for identifier={}", context, identifier, ex);
      throw new ExternalApiException(ErrorCode.EXTERNAL_API_BAD_RESPONSE, "TMDB request failed", ex);
    } catch (SearchProviderException | ExternalApiException ex) {
      throw ex;
    } catch (Exception ex) {
      logger.error("Unexpected error during TMDB {}", context, ex);
      throw new SearchProviderException(ErrorCode.SEARCH_FAILED, "Search provider failed", ex);
    }
  }

  private boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  private String getString(Map<String, Object> metadata, String key) {
    Object value = metadata.get(key);
    return value != null ? String.valueOf(value) : null;
  }

  @SuppressWarnings("unused")
  private static class TmdbSearchResponse {
    private List<TmdbSearchResult> results;

    public List<TmdbSearchResult> getResults() {
      return results;
    }

    public void setResults(List<TmdbSearchResult> results) {
      this.results = results;
    }
  }

  @SuppressWarnings("unused")
  private static class TmdbSearchResult {
    private Long id;
    private String title;
    private String overview;

    @com.fasterxml.jackson.annotation.JsonProperty("poster_path")
    private String posterPath;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getOverview() {
      return overview;
    }

    public void setOverview(String overview) {
      this.overview = overview;
    }

    public String getPosterPath() {
      return posterPath;
    }

    public void setPosterPath(String posterPath) {
      this.posterPath = posterPath;
    }
  }
}
