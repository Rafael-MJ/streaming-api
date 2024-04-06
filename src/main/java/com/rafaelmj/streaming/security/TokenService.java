package com.rafaelmj.streaming.security;

import com.rafaelmj.streaming.models.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User auth) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create()
        .withIssuer("haras-api")
        .withSubject(auth.getLogin())
        .withExpiresAt(genExpirationDate())
        .withClaim("userId", auth.getId().toString())
        .sign(algorithm);

      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token", exception);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
        .withIssuer("haras-api")
        .build()
        .verify(token)
        .getSubject();
    } catch(JWTVerificationException exception) {
      return "";
    }
  }

  public String getUserIdFromToken(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);

      return jwt.getClaim("userId").toString().replace("\"", "");
    } catch (JWTDecodeException exception) {
      return "";
    }
  }

  private Instant genExpirationDate() {
    return LocalDateTime.now().plusHours(48).toInstant(ZoneOffset.of("-03:00"));
  }
  
}
