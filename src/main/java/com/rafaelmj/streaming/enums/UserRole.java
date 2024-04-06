package com.rafaelmj.streaming.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
  MASTER("master"),
  ADMIN("admin"),
  USER("user");

  private final String role;
}
