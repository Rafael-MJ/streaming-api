package com.rafaelmj.streaming.services;

import com.rafaelmj.streaming.models.User;
import com.rafaelmj.streaming.repositories.UserRepository;
import com.rafaelmj.streaming.security.TokenService;
import com.rafaelmj.streaming.dtos.UserDTO;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
  
  @Autowired()
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  public Optional<User> getUserByToken(String token) {
    String userId = tokenService.getUserIdFromToken(token);

    return userRepository.findById(UUID.fromString(userId));
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
  
  public Optional<User> getUserById(UUID userId) {
    return userRepository.findById(userId);
  }
  
  @Transactional()
  public Optional<User> updateUserById(UserDTO userDto, UUID userId) {
    var userModel = userRepository.findById(userId);

    if (userModel.isEmpty()) {
      return Optional.empty();
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());

    UserDTO updatedDto = new UserDTO(
      userDto.login(),
      userDto.firstName(),
      userDto.lastName(),
      userDto.role(),
      encryptedPassword
    );

    var updatedUser = userModel.get();
    BeanUtils.copyProperties(updatedDto, updatedUser);

    return Optional.of(userRepository.save(updatedUser));
  }
  
}
