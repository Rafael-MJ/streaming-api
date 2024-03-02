package com.rafaelmj.spring.streamingapi.controllers;

import com.rafaelmj.spring.streamingapi.dtos.MovieRecordDTO;
import com.rafaelmj.spring.streamingapi.models.MovieModel;
import com.rafaelmj.spring.streamingapi.services.MovieService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping()
    public ResponseEntity<MovieModel> saveMovie(@RequestBody @Valid MovieRecordDTO newMovieDto) {
        return movieService.saveMovie(newMovieDto);
    }

    @GetMapping()
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getMovieById(@PathVariable(value = "id") UUID movieId) {
        return movieService.getMovieById(movieId);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Object> updateMovieById(@RequestBody @Valid MovieRecordDTO movieDto,
                                                  @PathVariable(value = "id") UUID movieId) {
        return movieService.updateMovieById(movieDto, movieId);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Object> deleteMovieById(@PathVariable(value = "id") UUID movieId) {
        return movieService.deleteMovieById(movieId);
    }
}
