package com.rafaelmj.streaming.dtos;

import com.rafaelmj.streaming.enums.MovieGender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record MovieDTO(
  @NotBlank String name,
  @NotNull MovieGender gender,
  @NotNull boolean isSensitive,
  @NotNull double minuteLength,
  @NotNull Timestamp releaseDate
) {}
