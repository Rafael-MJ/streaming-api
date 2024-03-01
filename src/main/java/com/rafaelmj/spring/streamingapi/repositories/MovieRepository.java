package com.rafaelmj.spring.streamingapi.repositories;

import com.rafaelmj.spring.streamingapi.models.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, UUID> {
}
