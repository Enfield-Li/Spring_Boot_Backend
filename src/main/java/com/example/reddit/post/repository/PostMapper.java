package com.example.reddit.post.repository;

import com.example.reddit.post.Post;
import com.example.reddit.post.dto.PostWithUserInfo;
import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;
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
public interface PostMapper {
  @Select(
    "SELECT p.id, p.title, p.content, u.id as userId, u.username FROM post p" +
    " LEFT JOIN user u ON p.user_id = u.id WHERE p.id = #{postId};"
  )
  @Results(
    value = {
      //   @Result(property = "id", column = "id", id = true),
      //   @Result(property = "title", column = "title"),
      //   @Result(property = "content", column = "content"),
      @Result(property = "user.userId", column = "userId"),
      @Result(property = "user.username", column = "username"),
    }
  )
  public PostWithUserInfo getPostWithUserInfoById(Long postId);

  @Select("SELECT * FROM post")
  public List<Post> getAllPosts();
}
