package com.rafaelmj.streaming.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.sql.Timestamp;

public class TimeUtil {

  public static Timestamp getCurrentTimestamp() {
    LocalDateTime currentTime = LocalDateTime.now();
    ZoneId brazilZone = ZoneId.of("America/Sao_Paulo");
    ZonedDateTime zonedDateTime = currentTime.atZone(brazilZone);
    Instant instant = zonedDateTime.toInstant();

    return Timestamp.from(instant);
  }
  
}
