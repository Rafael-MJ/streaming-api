package com.rafaelmj.streaming.controllers;

import com.rafaelmj.streaming.dtos.MovieDTO;
import com.rafaelmj.streaming.models.Movie;
import com.rafaelmj.streaming.models.User;
import com.rafaelmj.streaming.services.MovieService;
import com.rafaelmj.streaming.services.UserService;

import com.rafaelmj.streaming.utils.PaginatedResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
public class MovieController {
  
  @Autowired()
  private MovieService movieService;

  @Autowired()
  private UserService userService;

  @PostMapping()
  public ResponseEntity<Object> register(@RequestBody @Valid MovieDTO movieDTO, HttpServletRequest request) {
    String token = request.getHeader("Authorization").replace("Bearer ", "");
    Optional<User> user = userService.getUserByToken(token);

    if (user.isPresent()) {
      User userModel = user.get();

      return ResponseEntity.status(HttpStatus.OK).body(movieService.registerMovie(movieDTO, userModel));
    }
    
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error identifying user by token");
  }

  @GetMapping()
  public ResponseEntity<PaginatedResponseUtil<Movie>> getAllMovies(
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size ) {
    List<Movie> movies = movieService.getAllMovies(page, size);

    PaginatedResponseUtil<Movie> paginatedResponse = new PaginatedResponseUtil<>(
      movies,
      page,
      movieService.getMoviesTotalPages(size),
      movieService.getMoviesTotalElements()
    );

    return ResponseEntity.status(HttpStatus.OK).body(paginatedResponse);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Object> getMoviesByName(@PathVariable(value = "name") String movieName) {
    List<Movie> movies = movieService.getMoviesByName(movieName);

    return ResponseEntity.status(HttpStatus.OK).body(movies);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getMovieById(@PathVariable(value = "id") UUID movieId) {
    Optional<Movie> movieModel = movieService.getMovieById(movieId);

    if (movieModel.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie id not found");
    }

    return ResponseEntity.status(HttpStatus.OK).body(movieModel);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateMovieById(@RequestBody @Valid MovieDTO movieDto,
                   @PathVariable(value = "id") UUID movieId, HttpServletRequest request) {
    String token = request.getHeader("Authorization").replace("Bearer ", "");
    Optional<User> user = userService.getUserByToken(token);

    if (user.isPresent()) {
      User userModel = user.get();
      Optional<Movie> movieModel = movieService.updateMovieById(movieDto, movieId, userModel);

      if (movieModel.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie id not found");
      }

      return ResponseEntity.status(HttpStatus.OK).body(movieModel);

    }
    
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error identifying user by token");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteMovieById(@PathVariable(value = "id") UUID movieId) {
    Optional<Movie> movieModel = movieService.deleteMovieById(movieId);

    if (movieModel.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie id not found");
    }

    return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfuly");
  }

}
