package com.chuuzr.chuuzrbackend.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.chuuzr.chuuzrbackend.dto.user.UserMapper;
import com.chuuzr.chuuzrbackend.dto.user.UserRequestDTO;
import com.chuuzr.chuuzrbackend.dto.user.UserResponseDTO;
import com.chuuzr.chuuzrbackend.model.User;
import com.chuuzr.chuuzrbackend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/{userUuid}")
  public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID userUuid) {
    User user = findUser(userUuid);
    if (user != null) {
      return ResponseEntity.ok(UserMapper.toResponseDTO(user));
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody UserRequestDTO newUserRequest, UriComponentsBuilder ucb) {
    User userToSave = UserMapper.toEntity(newUserRequest);
    User savedUser = userRepository.save(userToSave);
    URI locationOfNewUser = ucb.path("/api/users/{userUuid}").buildAndExpand(savedUser.getUuid()).toUri();
    return ResponseEntity.created(locationOfNewUser).build();
  }

  @PutMapping("/{userUuid}")
  public ResponseEntity<Void> updateUser(@PathVariable UUID userUuid, @RequestBody UserRequestDTO userToUpdate) {
    User user = findUser(userUuid);
    if (user != null) {
      UserMapper.updateEntityFromDTO(user, userToUpdate);
      userRepository.save(user);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private User findUser(UUID userUuid) {
    return userRepository.findByUuid(userUuid).orElse(null);
  }
}