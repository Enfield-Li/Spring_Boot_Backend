package com.example.reddit.user.repository;

import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;
import com.example.reddit.user.dto.UserProfile;
import com.example.reddit.user.dto.UserWithPost;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
  @Select("SELECT * FROM user WHERE username = #{username}")
  public User findUserByIdWM(String username);

  @Select("SELECT id, username FROM user WHERE id = #{id}")
  public UserInfo findUserInfoByIdWM(Long id);

  @Select(
    "SELECT u.id, u.username, p.id AS postId, p.title, p.content" +
    " FROM user u LEFT JOIN post p ON u.id = p.user_id WHERE u.id = #{userId}"
  )
  public List<UserWithPost> findUserProfile(Long userId);
}
