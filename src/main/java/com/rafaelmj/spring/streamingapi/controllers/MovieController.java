package com.rafaelmj.spring.streamingapi.controllers;

import com.rafaelmj.spring.streamingapi.dtos.MovieRecordDTO;
import com.rafaelmj.spring.streamingapi.models.MovieModel;
import com.rafaelmj.spring.streamingapi.repositories.MovieRepository;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @PostMapping()
    public ResponseEntity<MovieModel> saveMovie (@RequestBody @Valid MovieRecordDTO newMovieDto) {
        var newMovieModel = new MovieModel();
        BeanUtils.copyProperties(newMovieDto, newMovieModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(newMovieModel));
    }
}
