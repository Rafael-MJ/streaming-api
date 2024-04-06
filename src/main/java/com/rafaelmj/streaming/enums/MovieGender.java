package com.rafaelmj.streaming.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MovieGender {
  HORROR("horror"),
  FICTION("fiction"),
  ROMANCE("romance"),
  ACTION("action"),
  ADVENTURE("adventure"),
  DRAMA("drama"),
  ANIMATION("animation"),
  DOCUMENTARY("documentary"),
  COMEDY("comedy"),
  FANTASY("fantasy");
  
  private final String gender;
}
