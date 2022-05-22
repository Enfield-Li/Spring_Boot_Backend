package com.example.reddit.user.repository;

import com.example.reddit.user.User;
import com.example.reddit.user.repository.custom.UserMapperRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
  extends JpaRepository<User, Long>, UserMapperRepository {}
