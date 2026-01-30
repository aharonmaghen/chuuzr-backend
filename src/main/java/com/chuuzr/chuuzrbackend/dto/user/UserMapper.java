package com.chuuzr.chuuzrbackend.dto.user;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.chuuzr.chuuzrbackend.dto.auth.UserAuthResponse;
import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.ValidationException;
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.util.ValidationUtil;

public class UserMapper {

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

  public static User toEntity(UserRequestDTO dto) {
    if (dto == null) {
      return null;
    }
    User user = new User();
    user.setName(dto.getName());
    user.setNickname(dto.getNickname());
    user.setCountryCode(dto.getCountryCode());

    String normalizedPhone = ValidationUtil.normalizePhoneNumber(dto.getPhoneNumber(), dto.getCountryCode());
    if (normalizedPhone == null) {
      throw new ValidationException(ErrorCode.FIELD_INVALID_FORMAT,
          Map.of("phoneNumber", List.of("Invalid phone number for country code: " + dto.getCountryCode())));
    }
    user.setPhoneNumber(normalizedPhone);

    if (dto.getProfilePicture() != null && !dto.getProfilePicture().trim().isEmpty()) {
      try {
        user.setProfilePicture(new URL(dto.getProfilePicture().trim()));
      } catch (MalformedURLException e) {
        throw new ValidationException(ErrorCode.FIELD_INVALID_FORMAT,
            Map.of("profilePicture", List.of("Invalid URL format: " + e.getMessage())));
      }
    }

    return user;
  }

  public static void updateEntityFromDTO(User user, UserRequestDTO dto) {
    if (user == null || dto == null) {
      return;
    }
    user.setName(dto.getName());
    user.setNickname(dto.getNickname());
    user.setCountryCode(dto.getCountryCode());

    String normalizedPhone = ValidationUtil.normalizePhoneNumber(dto.getPhoneNumber(), dto.getCountryCode());
    if (normalizedPhone == null) {
      throw new ValidationException(ErrorCode.FIELD_INVALID_FORMAT,
          Map.of("phoneNumber", List.of("Invalid phone number for country code: " + dto.getCountryCode())));
    }
    user.setPhoneNumber(normalizedPhone);

    if (dto.getProfilePicture() != null && !dto.getProfilePicture().trim().isEmpty()) {
      try {
        user.setProfilePicture(new URL(dto.getProfilePicture().trim()));
      } catch (MalformedURLException e) {
        throw new ValidationException(ErrorCode.FIELD_INVALID_FORMAT,
            Map.of("profilePicture", List.of("Invalid URL format: " + e.getMessage())));
      }
    } else {
      user.setProfilePicture(null);
    }
  }

  public static UserInternalDTO toInternalDTO(User user) {
    if (user == null) {
      return null;
    }
    Set<String> roles = Set.of("ROLE_USER");
    return new UserInternalDTO(user.getUuid(), user.getName(), user.getNickname(), roles);
  }

  public static UserAuthResponse toAuthResponse(String jwt, UUID userUuid, boolean requiresRegistration) {
    return new UserAuthResponse(jwt, userUuid, requiresRegistration);
  }
}
