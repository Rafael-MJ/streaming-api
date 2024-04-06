package com.rafaelmj.streaming.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rafaelmj.streaming.enums.MovieGender;

import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Movies")
public class Movie implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false)
  private String name;
  
  @Column(nullable = false)
  private MovieGender gender;

  @Column(nullable = false)
  private Timestamp createdAt;

  @JsonProperty(value="isSensitive")
  private boolean isSensitive;

  @ManyToOne
  @JoinColumn
  private User viewedBy;

  private double minuteLength;
  private Timestamp releaseDate;

  public Movie(String name, MovieGender gender, Timestamp createdAt, boolean isSensitive, double minuteLength, Timestamp releaseDate) {
    this.name = name;
    this.gender = gender;
    this.createdAt = createdAt;
    this.isSensitive = isSensitive;
    this.minuteLength = minuteLength;
    this.releaseDate = releaseDate;
  }
  
}
