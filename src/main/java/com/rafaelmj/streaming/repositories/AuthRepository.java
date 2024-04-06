package com.rafaelmj.streaming.repositories;

import com.rafaelmj.streaming.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<User, UUID> {

  UserDetails findByLogin(String login);
  
}
