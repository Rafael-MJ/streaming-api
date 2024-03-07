package com.rafaelmj.spring.streamingapi.services;

import com.rafaelmj.spring.streamingapi.dtos.MovieRecordDTO;
import com.rafaelmj.spring.streamingapi.models.MovieModel;
import com.rafaelmj.spring.streamingapi.repositories.MovieRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired()
    MovieRepository movieRepository;

    @Transactional()
    public ResponseEntity<MovieModel> saveMovie(MovieRecordDTO newMovieDto) {
        var newMovieModel = new MovieModel();
        BeanUtils.copyProperties(newMovieDto, newMovieModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(newMovieModel));
    }

    public ResponseEntity<List<MovieModel>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(movieRepository.findAll());
    }

    public ResponseEntity<Object> getMovieById(UUID movieId) {
        Optional<MovieModel> movieModel = movieRepository.findById(movieId);

        if (movieModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie id not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(movieModel);
    }

    @Transactional()
    public ResponseEntity<Object> updateMovieById(MovieRecordDTO movieDto, UUID movieId) {
        Optional<MovieModel> movieModel = movieRepository.findById(movieId);

        if (movieModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie id not found");
        }

        var updatedMovie = movieModel.get();
        BeanUtils.copyProperties(movieDto, updatedMovie);
        return ResponseEntity.status(HttpStatus.OK).body(movieRepository.save(updatedMovie));
    }

    @Transactional()
    public ResponseEntity<Object> deleteMovieById(UUID movieId) {
        Optional<MovieModel> movieModel = movieRepository.findById(movieId);

        if (movieModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie id not found");
        }

        movieRepository.delete(movieModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfuly");
    }
}
