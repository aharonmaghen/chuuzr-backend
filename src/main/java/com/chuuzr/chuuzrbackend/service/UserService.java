package com.chuuzr.chuuzrbackend.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chuuzr.chuuzrbackend.dto.user.UserCreateRequestDTO;
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
import com.chuuzr.chuuzrbackend.util.RedisKeyConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final StringRedisTemplate stringRedisTemplate;
  private final ObjectMapper objectMapper;

  @Autowired
  public UserService(UserRepository userRepository, JwtUtil jwtUtil,
      StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
    this.stringRedisTemplate = stringRedisTemplate;
    this.objectMapper = objectMapper;
  }

  @Transactional(readOnly = true)
  public UserResponseDTO findByUuid(UUID userUuid) {
    logger.debug("Finding user by uuid={}", userUuid);
    User user = userRepository.findByUuid(userUuid).orElseThrow(
        () -> new UserNotFoundException("User with UUID " + userUuid + " not found"));
    return UserMapper.toResponseDTO(user);
  }

  public UserCreatedResponseDTO createUser(UserCreateRequestDTO userCreateRequestDTO) {
    logger.debug("Creating user from registration token");

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String preRegUuid = (String) authentication.getPrincipal();

    String redisKey = RedisKeyConstants.PRE_REG_PREFIX + preRegUuid;
    String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
    if (redisValue == null) {
      throw new AuthorizationException(ErrorCode.JWT_INVALID, "Registration data expired or already used");
    }
    stringRedisTemplate.delete(redisKey);

    String countryCode;
    String phoneNumber;
    try {
      JsonNode node = objectMapper.readTree(redisValue);
      countryCode = node.get("countryCode").asText();
      phoneNumber = node.get("phoneNumber").asText();
    } catch (Exception e) {
      throw new AuthorizationException(ErrorCode.JWT_INVALID, "Failed to parse registration data");
    }

    User userToSave = UserMapper.toEntity(userCreateRequestDTO, countryCode, phoneNumber);
    User savedUser = userRepository.save(userToSave);
    logger.debug("User created and token generated for uuid={}", savedUser.getUuid());
    UserResponseDTO userResponseDTO = UserMapper.toResponseDTO(savedUser);
    String token = jwtUtil.generateToken(savedUser.getUuid());
    return new UserCreatedResponseDTO(token, userResponseDTO);
  }

  public UserResponseDTO updateUser(UUID userUuid, UserRequestDTO userRequestDTO) {
    logger.debug("Updating user with uuid={}", userUuid);
    User user = userRepository.findByUuid(userUuid).orElseThrow(
        () -> new UserNotFoundException("User with UUID " + userUuid + " not found"));
    UserMapper.updateEntityFromDTO(user, userRequestDTO);
    User updatedUser = userRepository.save(user);
    logger.info("User updated with uuid={}", userUuid);
    return UserMapper.toResponseDTO(updatedUser);
  }
}
