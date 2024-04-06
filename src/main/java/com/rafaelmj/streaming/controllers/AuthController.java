package com.rafaelmj.streaming.controllers;

import com.rafaelmj.streaming.dtos.AuthDTO;
import com.rafaelmj.streaming.models.User;
import com.rafaelmj.streaming.security.TokenService;
import com.rafaelmj.streaming.dtos.LoginTokenDTO;
import com.rafaelmj.streaming.dtos.UserDTO;
import com.rafaelmj.streaming.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AuthService authService;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody @Valid AuthDTO authData) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(authData.login(), authData.password());

    try {
      var auth = authenticationManager.authenticate(usernamePassword);
      var token = tokenService.generateToken((User) auth.getPrincipal());

      return ResponseEntity.status(HttpStatus.OK).body(new LoginTokenDTO(token));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR: Invalid credentials");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody @Valid UserDTO userDTO) {
    if (authService.loadUserByUsername(userDTO.login()) != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");

    }
    
    return ResponseEntity.status(HttpStatus.OK).body(authService.registerUser(userDTO));
  }

  @GetMapping("/relogin")
  public ResponseEntity<Object> relogin() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    User userDetails = (User) authentication.getPrincipal();
    String newToken = tokenService.generateToken(userDetails);

    return ResponseEntity.status(HttpStatus.OK).body(new LoginTokenDTO(newToken));
  }
  
}
