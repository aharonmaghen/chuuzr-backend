package com.chuuzr.chuuzrbackend.dto.user;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.auth.UserAuthResponse;
import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.model.User;

/**
 * Mapper utility for converting between User entity and DTOs.
 */
public class UserMapper {

  /**
   * Converts a User entity to a UserResponseDTO.
   */
  public static UserResponseDTO toResponseDTO(User user) {
    if (user == null) {
      return null;
    }
    return new UserResponseDTO(
        user.getUuid(),
        user.getName(),
        user.getNickname(),
        user.getCountryCode(),
        user.getPhoneNumber(),
        user.getProfilePicture(),
        user.getUpdatedAt(),
        user.getCreatedAt());
  }

  /**
   * Converts a UserRequestDTO to a User entity.
   * Note: ID, UUID, and timestamps should be set separately.
   */
  public static User toEntity(UserRequestDTO dto) {
    if (dto == null) {
      return null;
    }
    User user = new User();
    user.setName(dto.getName());
    user.setNickname(dto.getNickname());
    user.setCountryCode(dto.getCountryCode());
    user.setPhoneNumber(dto.getPhoneNumber());
    user.setProfilePicture(dto.getProfilePicture());
    return user;
  }

  /**
   * Updates an existing User entity with data from UserRequestDTO.
   * Preserves ID, UUID, and createdAt timestamp.
   */
  public static void updateEntityFromDTO(User user, UserRequestDTO dto) {
    if (user == null || dto == null) {
      return;
    }
    user.setName(dto.getName());
    user.setNickname(dto.getNickname());
    user.setCountryCode(dto.getCountryCode());
    user.setPhoneNumber(dto.getPhoneNumber());
    user.setProfilePicture(dto.getProfilePicture());
  }

  public static UserInternalDTO toInternalDTO(User user) {
    if (user == null) {
      return null;
    }
    Set<String> roles = Collections.emptySet();
    return new UserInternalDTO(user.getUuid(), user.getName(), user.getNickname(), roles);
  }

  public static UserAuthResponse toAuthResponse(String jwt, UUID userUuid) {
    return new UserAuthResponse(jwt, userUuid);
  }
}
