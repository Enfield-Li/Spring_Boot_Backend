package com.example.reddit.user.repository.custom;

import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;
import com.example.reddit.user.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryImpl implements UserMapperRepository {

  private UserMapper userMapper;

  @Autowired
  UserRepositoryImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public User findUserByIdWM(String username) {
    return userMapper.findUserByIdWM(username);
  }

  @Override
  public UserInfo findUserInfoByIdWM(Long id) {
    return userMapper.findUserInfoByIdWM(id);
  }
}
