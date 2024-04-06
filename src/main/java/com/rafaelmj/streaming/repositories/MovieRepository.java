package com.rafaelmj.streaming.repositories;

import com.rafaelmj.streaming.models.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {

  List<Movie> findAllByName(String name);

}
