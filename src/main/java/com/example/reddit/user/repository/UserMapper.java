package com.example.reddit.user.repository;

import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM user WHERE username = #{username}")
  public User findUserByIdWM(String username);

  @Select("SELECT id, username FROM user WHERE id = #{id}")
  public UserInfo findUserInfoByIdWM(Long id);
}
