package com.rafaelmj.streaming;

import com.rafaelmj.streaming.dtos.UserDTO;
import com.rafaelmj.streaming.enums.UserRole;
import com.rafaelmj.streaming.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StreamingApplication {

  // TODO: Remove in production
  @Bean
  public CommandLineRunner init(@Autowired AuthService authService) {
    return args -> {
      if (authService.loadUserByUsername("master@email.com") == null) {

        UserDTO masterUser = new UserDTO(
          "master@email.com",
          "master-user",
          "master-user",
          UserRole.MASTER,
          "master"
        );

        authService.registerUser(masterUser);
      }

      System.out.println(
        """
          
          =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
          
           CAUTION: Master User registered automatically!
           
           LOGIN: master@email.com
           PASSWORD: master
           
           REMOVE METHOD BEFORE RUN IN PRODUCTION:
           'com.rafaelmj.streaming/StreamingApplication.java:init'
           
          =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
           
         """);

    };
  }

  public static void main(String[] args) {
    SpringApplication.run(StreamingApplication.class, args);
  }
  
}
