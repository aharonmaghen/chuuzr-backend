package com.chuuzr.chuuzrbackend.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.search.SearchRequestDTO;
import com.chuuzr.chuuzrbackend.dto.search.SearchResponseDTO;
import com.chuuzr.chuuzrbackend.dto.search.SearchResultDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.BaseException;
import com.chuuzr.chuuzrbackend.exception.SearchProviderException;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;

@Service
@Transactional
public class SearchService {
  private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

  private final RoomRepository roomRepository;
  private final SearchProviderFactory searchProviderFactory;

  @Autowired
  public SearchService(RoomRepository roomRepository, SearchProviderFactory searchProviderFactory) {
    this.roomRepository = roomRepository;
    this.searchProviderFactory = searchProviderFactory;
  }

  @Transactional(readOnly = true)
  public SearchResponseDTO search(UUID roomUuid, SearchRequestDTO request, Pageable pageable) {
    logger.debug("Search request for roomUuid={}, query={}", roomUuid, request.getQuery());

    try {
      Room room = roomRepository.findByUuid(roomUuid)
          .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND,
              "Room with UUID " + roomUuid + " not found"));

      List<SearchProviderService> providers = searchProviderFactory.getProvidersForRoom(room);
      List<SearchResultDTO> combinedResults = new ArrayList<>();
      for (SearchProviderService provider : providers) {
        SearchResponseDTO response = provider.search(request, pageable);
        if (response != null && response.getResults() != null) {
          combinedResults.addAll(response.getResults());
        }
      }
      logger.info("Search completed for roomUuid={}, resultCount={}", roomUuid, combinedResults.size());
      return new SearchResponseDTO(combinedResults);
    } catch (ResourceNotFoundException ex) {
      throw ex;
    } catch (BaseException ex) {
      logger.warn("Search failed for roomUuid={} with errorCode={}", roomUuid, ex.getErrorCode());
      throw ex;
    } catch (Exception ex) {
      logger.error("Search failed for roomUuid={}", roomUuid, ex);
      throw new SearchProviderException(ErrorCode.SEARCH_FAILED, "Search failed", ex);
    }
  }
}
