package com.rafaelmj.streaming.services;

import com.rafaelmj.streaming.dtos.MovieDTO;
import com.rafaelmj.streaming.models.Movie;
import com.rafaelmj.streaming.models.User;
import com.rafaelmj.streaming.repositories.MovieRepository;
import com.rafaelmj.streaming.utils.TimeUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {
  
  @Autowired()
  private MovieRepository movieRepository;

  @Transactional()
  public Movie registerMovie(MovieDTO movieDTO, User authUser) {
    var newMovie = new Movie();

    BeanUtils.copyProperties(movieDTO, newMovie);
    newMovie.setCreatedAt(TimeUtil.getCurrentTimestamp());

    return movieRepository.save(newMovie);
  }

  public List<Movie> getAllMovies(int page, int size) {
    Sort sort = Sort.by(Sort.Direction.ASC, "name");
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Movie> moviesPage = movieRepository.findAll(pageable);

    return moviesPage.getContent();
  }

  public long getMoviesTotalElements() {
    return movieRepository.count();
  }

  public int getMoviesTotalPages(int size) {
    Pageable pageable = PageRequest.of(0, size);
    long totalElements = getMoviesTotalElements();

    return (int) Math.ceil((double) totalElements / (double) size);
  }

  public List<Movie> getMoviesByName(String movieName) {
    return movieRepository.findAllByName(movieName);
  }
  
  public Optional<Movie> getMovieById(UUID movieId) {
    return movieRepository.findById(movieId);
  }

  @Transactional()
  public Optional<Movie> updateMovieById(MovieDTO movieDto, UUID movieId, User authUser) {
    var movieModel = movieRepository.findById(movieId);

    if (movieModel.isEmpty()) {
      return Optional.empty();
    }

    var updatedMovie = movieModel.get();
    BeanUtils.copyProperties(movieDto, updatedMovie);

    return Optional.of(movieRepository.save(updatedMovie));
  }

  @Transactional()
  public Optional<Movie> deleteMovieById(UUID movieId) {
    var movieModel = movieRepository.findById(movieId);

    if (movieModel.isEmpty()) {
      return Optional.empty();
    }

    movieRepository.delete(movieModel.get());
    
    return movieModel;
  }

}
