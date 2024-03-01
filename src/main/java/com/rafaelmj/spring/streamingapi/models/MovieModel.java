package com.rafaelmj.spring.streamingapi.models;

import com.rafaelmj.spring.streamingapi.enums.MovieCategory;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Movies")
public class MovieModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID movieId;
    private String movieTitle;
    private MovieCategory movieCategory;
    private double movieLength;

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public MovieCategory getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(MovieCategory movieCategory) {
        this.movieCategory = movieCategory;
    }

    public double getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(double movieLength) {
        this.movieLength = movieLength;
    }
}
