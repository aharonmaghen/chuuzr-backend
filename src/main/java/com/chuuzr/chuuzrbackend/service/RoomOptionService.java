package com.chuuzr.chuuzrbackend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.option.OptionMapper;
import com.chuuzr.chuuzrbackend.dto.option.OptionResponseDTO;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionMapper;
import com.chuuzr.chuuzrbackend.dto.roomoption.RoomOptionResponseDTO;
import com.chuuzr.chuuzrbackend.model.Option;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomOption;
import com.chuuzr.chuuzrbackend.repository.OptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomOptionRepository;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;

/**
 * Service layer for RoomOption business logic.
 * Handles transactions and coordinates between controller and repository.
 */
@Service
@Transactional
public class RoomOptionService {

  private final RoomOptionRepository roomOptionRepository;
  private final RoomRepository roomRepository;
  private final OptionRepository optionRepository;

  @Autowired
  public RoomOptionService(RoomOptionRepository roomOptionRepository, RoomRepository roomRepository,
      OptionRepository optionRepository) {
    this.roomOptionRepository = roomOptionRepository;
    this.roomRepository = roomRepository;
    this.optionRepository = optionRepository;
  }

  /**
   * Gets all options in a room (paginated).
   *
   * @param roomUuid The UUID of the room
   * @param pageable Pagination parameters
   * @return List of options in the room
   */
  @Transactional(readOnly = true)
  public List<OptionResponseDTO> getRoomOptions(UUID roomUuid, Pageable pageable) {
    Page<RoomOption> page = roomOptionRepository.findByRoomUuid(roomUuid, pageable);
    return page.getContent().stream().map(RoomOption::getOption).map(OptionMapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  /**
   * Adds an option to a room.
   *
   * @param roomUuid   The UUID of the room
   * @param optionUuid The UUID of the option to add
   * @return The created RoomOption relationship as a response DTO, or null if
   *         room
   *         or option not found
   */
  public RoomOptionResponseDTO addOptionToRoom(UUID roomUuid, UUID optionUuid) {
    Room room = roomRepository.findByUuid(roomUuid).orElse(null);
    Option option = optionRepository.findByUuid(optionUuid).orElse(null);
    if (room == null || option == null) {
      return null;
    }
    RoomOption roomOption = RoomOptionMapper.toEntity(room, option);
    RoomOption savedRoomOption = roomOptionRepository.save(roomOption);
    return RoomOptionMapper.toResponseDTO(savedRoomOption);
  }
}
