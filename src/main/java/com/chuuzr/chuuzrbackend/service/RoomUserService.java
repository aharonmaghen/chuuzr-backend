package com.chuuzr.chuuzrbackend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserMapper;
import com.chuuzr.chuuzrbackend.dto.roomuser.RoomUserResponseDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;
import com.chuuzr.chuuzrbackend.repository.RoomUserRepository;
import com.chuuzr.chuuzrbackend.repository.UserRepository;

/**
 * Service layer for RoomUser business logic.
 * Handles transactions and coordinates between controller and repository.
 */
@Service
@Transactional
public class RoomUserService {

  private final RoomUserRepository roomUserRepository;
  private final RoomRepository roomRepository;
  private final UserRepository userRepository;

  @Autowired
  public RoomUserService(RoomUserRepository roomUserRepository, RoomRepository roomRepository,
      UserRepository userRepository) {
    this.roomUserRepository = roomUserRepository;
    this.roomRepository = roomRepository;
    this.userRepository = userRepository;
  }

  /**
   * Gets all users in a room (paginated).
   *
   * @param roomUuid The UUID of the room
   * @param pageable Pagination parameters
   * @return List of users in the room
   */
  @Transactional(readOnly = true)
  public List<UserResponseDTO> getRoomUsers(UUID roomUuid, Pageable pageable) {
    Page<RoomUser> page = roomUserRepository.findByRoomUuid(roomUuid, pageable);
    return page.getContent().stream().map(RoomUser::getUser).map(UserMapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  /**
   * Adds a user to a room.
   *
   * @param roomUuid The UUID of the room
   * @param userUuid The UUID of the user to add
   * @return The created RoomUser relationship as a response DTO, or null if room
   *         or user not found
   */
  public RoomUserResponseDTO addUserToRoom(UUID roomUuid, UUID userUuid) {
    Room room = roomRepository.findByUuid(roomUuid).orElse(null);
    User user = userRepository.findByUuid(userUuid).orElse(null);
    if (room == null || user == null) {
      return null;
    }
    RoomUser roomUserToSave = RoomUserMapper.toEntity(room, user);
    RoomUser savedRoomUser = roomUserRepository.save(roomUserToSave);
    return RoomUserMapper.toResponseDTO(savedRoomUser);
  }
}
