package com.rafaelmj.streaming.dtos;

import com.rafaelmj.streaming.enums.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
  @NotBlank String login,
  @NotBlank String firstName,
  @NotBlank String lastName,
  @NotNull UserRole role,
  @NotBlank String password
) {}