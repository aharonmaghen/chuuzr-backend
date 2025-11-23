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
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ResourceNotFoundException;
import com.chuuzr.chuuzrbackend.exception.UserNotFoundException;
import com.chuuzr.chuuzrbackend.model.Room;
import com.chuuzr.chuuzrbackend.model.RoomUser;
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.repository.RoomRepository;
import com.chuuzr.chuuzrbackend.repository.RoomUserRepository;
import com.chuuzr.chuuzrbackend.repository.UserRepository;

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

  @Transactional(readOnly = true)
  public List<UserResponseDTO> getRoomUsers(UUID roomUuid, Pageable pageable) {
    Page<RoomUser> page = roomUserRepository.findByRoomUuid(roomUuid, pageable);
    return page.getContent().stream().map(RoomUser::getUser).map(UserMapper::toResponseDTO)
        .collect(Collectors.toList());
  }

  public RoomUserResponseDTO addUserToRoom(UUID roomUuid, UUID userUuid) {
    Room room = roomRepository.findByUuid(roomUuid)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ROOM_NOT_FOUND,
            "Room with UUID " + roomUuid + " not found"));

    User user = userRepository.findByUuid(userUuid)
        .orElseThrow(() -> new UserNotFoundException("User with UUID " + userUuid + " not found"));

    RoomUser roomUserToSave = RoomUserMapper.toEntity(room, user);
    RoomUser savedRoomUser = roomUserRepository.save(roomUserToSave);
    return RoomUserMapper.toResponseDTO(savedRoomUser);
  }
}
