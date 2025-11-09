package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
import com.chuuzr.chuuzrbackend.dto.user.UserRequestDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.repository.UserRepository;

/**
 * Service layer for User business logic.
 * Handles transactions and coordinates between controller and repository.
 */
@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Finds a user by UUID and returns the DTO.
   *
   * @param userUuid The UUID of the user to find
   * @return UserResponseDTO if found, null otherwise
   */
  @Transactional(readOnly = true)
  public UserResponseDTO findByUuid(UUID userUuid) {
    User user = userRepository.findByUuid(userUuid).orElse(null);
    return UserMapper.toResponseDTO(user);
  }

  /**
   * Creates a new user.
   *
   * @param userRequestDTO The user data to create
   * @return The created user as a response DTO
   */
  public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
    User userToSave = UserMapper.toEntity(userRequestDTO);
    User savedUser = userRepository.save(userToSave);
    return UserMapper.toResponseDTO(savedUser);
  }

  /**
   * Updates an existing user.
   *
   * @param userUuid       The UUID of the user to update
   * @param userRequestDTO The updated user data
   * @return The updated user as a response DTO, or null if user not found
   */
  public UserResponseDTO updateUser(UUID userUuid, UserRequestDTO userRequestDTO) {
    User user = userRepository.findByUuid(userUuid).orElse(null);
    if (user == null) {
      return null;
    }
    UserMapper.updateEntityFromDTO(user, userRequestDTO);
    User updatedUser = userRepository.save(user);
    return UserMapper.toResponseDTO(updatedUser);
  }
}
