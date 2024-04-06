package com.rafaelmj.streaming.controllers;

import com.rafaelmj.streaming.models.User;
import com.rafaelmj.streaming.dtos.UserDTO;
import com.rafaelmj.streaming.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
  
  @Autowired()
  private UserService userService;

  @GetMapping()
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
  }

  @GetMapping("/my-info")
  public ResponseEntity<Object> getUserByToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization").replace("Bearer ", "");
    Optional<User> user = userService.getUserByToken(token);

    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getUserById(@PathVariable(value = "id") UUID userId) {
    Optional<User> userModel = userService.getUserById(userId);

    if (userModel.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id not found");
    }

    return ResponseEntity.status(HttpStatus.OK).body(userModel);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateUserById(@RequestBody @Valid UserDTO userDto,
                                               @PathVariable(value = "id") UUID userId) {
    Optional<User> userModel = userService.updateUserById(userDto, userId);

    if (userModel.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User id not found");
    }

    return ResponseEntity.status(HttpStatus.OK).body(userModel);
  }
  
}
