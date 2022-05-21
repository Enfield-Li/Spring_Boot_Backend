package com.example.reddit.user;

import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryImpl implements UserMapperRepository {

  private UserMapper userMapper;

  @Autowired
  UserRepositoryImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }
}
