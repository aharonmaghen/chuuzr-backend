package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.user.UserCreatedResponseDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
import com.chuuzr.chuuzrbackend.dto.user.UserRequestDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.AuthorizationException;
import com.chuuzr.chuuzrbackend.exception.UserNotFoundException;
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.repository.UserRepository;
import com.chuuzr.chuuzrbackend.security.JwtUtil;
import com.chuuzr.chuuzrbackend.util.CountryCodeUtil;
import com.chuuzr.chuuzrbackend.util.ValidationUtil;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  @Autowired
  public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findByUuid(UUID userUuid) {
    User user = userRepository.findByUuid(userUuid).orElseThrow(
        () -> new UserNotFoundException("User with UUID " + userUuid + " not found"));
    return UserMapper.toResponseDTO(user);
  }

  public UserCreatedResponseDTO createUser(UserRequestDTO userRequestDTO) {
    validatePreRegisterTokenPhoneNumber(userRequestDTO);

    User userToSave = UserMapper.toEntity(userRequestDTO);
    User savedUser = userRepository.save(userToSave);
    UserResponseDTO userResponseDTO = UserMapper.toResponseDTO(savedUser);
    String token = jwtUtil.generateToken(savedUser.getUuid());
    return new UserCreatedResponseDTO(token, userResponseDTO);
  }

  private void validatePreRegisterTokenPhoneNumber(UserRequestDTO userRequestDTO) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String tokenPhoneNumber = (String) authentication.getPrincipal();

    String normalizedPhone = ValidationUtil.normalizePhoneNumber(userRequestDTO.getPhoneNumber(),
        userRequestDTO.getCountryCode());
    if (normalizedPhone == null) {
      throw new AuthorizationException(ErrorCode.INVALID_INPUT,
          "Invalid phone number for country code: " + userRequestDTO.getCountryCode());
    }
    String requestedPhoneNumber = CountryCodeUtil.toDialCode(userRequestDTO.getCountryCode()) + normalizedPhone;

    if (!tokenPhoneNumber.equals(requestedPhoneNumber)) {
      throw new AuthorizationException(ErrorCode.AUTHORIZATION_FAILED,
          "Phone number in request does not match the phone number associated with the registration token");
    }
  }

  public UserResponseDTO updateUser(UUID userUuid, UserRequestDTO userRequestDTO) {
    User user = userRepository.findByUuid(userUuid).orElseThrow(
        () -> new UserNotFoundException("User with UUID " + userUuid + " not found"));
    UserMapper.updateEntityFromDTO(user, userRequestDTO);
    User updatedUser = userRepository.save(user);
    return UserMapper.toResponseDTO(updatedUser);
  }
}
