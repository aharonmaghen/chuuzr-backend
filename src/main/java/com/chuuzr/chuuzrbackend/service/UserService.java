package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
import com.chuuzr.chuuzrbackend.dto.user.UserRequestDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
import com.chuuzr.chuuzrbackend.exception.UserNotFoundException;
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.repository.UserRepository;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findByUuid(UUID userUuid) {
    User user = userRepository.findByUuid(userUuid).orElseThrow(
        () -> new UserNotFoundException("User with UUID " + userUuid + " not found"));
    return UserMapper.toResponseDTO(user);
  }

  public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
    User userToSave = UserMapper.toEntity(userRequestDTO);
    User savedUser = userRepository.save(userToSave);
    return UserMapper.toResponseDTO(savedUser);
  }

  public UserResponseDTO updateUser(UUID userUuid, UserRequestDTO userRequestDTO) {
    User user = userRepository.findByUuid(userUuid).orElseThrow(
        () -> new UserNotFoundException("User with UUID " + userUuid + " not found"));
    UserMapper.updateEntityFromDTO(user, userRequestDTO);
    User updatedUser = userRepository.save(user);
    return UserMapper.toResponseDTO(updatedUser);
  }
}
