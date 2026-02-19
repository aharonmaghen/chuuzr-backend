package com.chuuzr.chuuzrbackend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionMapper;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionResponseDTO;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionSummaryResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.BusinessLogicException;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.repository.RoomOptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;

@Service
@Transactional
public class RoomOptionService {

  private static final Logger logger = LoggerFactory.getLogger(RoomOptionService.class);

  private final RoomOptionRepository roomOptionRepository;
  private final RoomRepository roomRepository;
  private final OptionService optionService;

  @Autowired
  public RoomOptionService(RoomOptionRepository roomOptionRepository, RoomRepository roomRepository,
      OptionService optionService) {
    this.roomOptionRepository = roomOptionRepository;
    this.roomRepository = roomRepository;
    this.optionService = optionService;
  }

  @Transactional(readOnly = true)
  public List<RoomOptionSummaryResponseDTO> getRoomOptions(UUID roomUuid, Pageable pageable) {
    logger.debug("Fetching room options for roomUuid={}", roomUuid);
    Page<RoomOption> page = roomOptionRepository.findByRoomUuid(roomUuid, pageable);
    return page.getContent().stream().map(RoomOptionMapper::toSummaryDTO)
        .collect(Collectors.toList());
  }

  public RoomOptionResponseDTO addOptionToRoom(UUID roomUuid, String apiProvider, String externalId) {
    logger.debug("Adding option to room roomUuid={}, apiProvider={}, externalId={}", roomUuid, apiProvider, externalId);
    Room room = roomRepository.findByUuid(roomUuid).orElseThrow(
        () -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND,
            "Room with UUID " + roomUuid + " not found"));

    Option option = optionService.findOrCreateOption(apiProvider, externalId, room.getOptionType());

    roomOptionRepository.findByUuids(roomUuid, option.getUuid()).ifPresent(existing -> {
      throw new BusinessLogicException(ErrorCode.DUPLICATE_RESOURCE, "Option is already in this room");
    });

    RoomOption roomOptionToSave = RoomOptionMapper.toEntity(room, option);
    roomOptionToSave.setScore(0);
    RoomOption savedRoomOption = roomOptionRepository.save(roomOptionToSave);
    logger.info("Option added to room roomUuid={}, optionUuid={}", roomUuid, option.getUuid());
    return RoomOptionMapper.toResponseDTO(savedRoomOption);
  }
}
