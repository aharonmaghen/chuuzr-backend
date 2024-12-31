package com.movienight.movienightbackend.controllers;

import java.net.URI;

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

import com.movienight.movienightbackend.models.User;
import com.movienight.movienightbackend.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/{requestedId}")
  public ResponseEntity<User> findById(@PathVariable Long requestedId) {
    User user = findUser(requestedId);
    if (user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody User newuserRequest, UriComponentsBuilder ucb) {
    User userToSave = new User(null, newuserRequest.getName(), newuserRequest.getNickname(),
        newuserRequest.getCountryCode(), newuserRequest.getPhoneNumber(), newuserRequest.getProfilePicture(), null,
        null);
    User savedUser = userRepository.save(userToSave);
    URI locationOfNewUser = ucb.path("/api/users/{id}").buildAndExpand(savedUser.getId()).toUri();
    return ResponseEntity.created(locationOfNewUser).build();
  }

  @PutMapping("/{requestedId}")
  public ResponseEntity<Void> updateUser(@PathVariable Long requestedId, @RequestBody User userToUpdate) {
    User user = findUser(requestedId);
    if (user != null) {
      User updatedUser = new User(user.getId(), userToUpdate.getName(), userToUpdate.getNickname(),
          userToUpdate.getCountryCode(), userToUpdate.getPhoneNumber(), userToUpdate.getProfilePicture(), null, null);
      userRepository.save(updatedUser);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  private User findUser(Long requestedId) {
    return userRepository.findById(requestedId).orElse(null);
  }
}