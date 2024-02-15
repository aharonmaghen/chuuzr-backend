package com.movienight.movienightbackend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movienight.movienightbackend.models.User;
import com.movienight.movienightbackend.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userRepository.save(user);
  }

  @PutMapping("/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User userData) {
    userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    
    User user = User.create(id,
        userData.name(),
        userData.nickname(),
        userData.countryCode(),
        userData.phoneNumber(),
        userData.profilePicture(),
        Optional.empty(),
        Optional.empty());

    return userRepository.save(user);
  }
}
