package com.example.reddit.user.repository.custom;

import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;

public interface UserMapperRepository {
  public User findUserByIdWM(String username);

  public UserInfo findUserInfoByIdWM(Long id);
}
