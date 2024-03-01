package com.rafaelmj.spring.streamingapi.dtos;

import com.rafaelmj.spring.streamingapi.enums.MovieCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MovieRecordDTO(
        @NotBlank String movieTitle,
        @NotNull MovieCategory movieCategory,
        @NotNull double movieLength) {
}
