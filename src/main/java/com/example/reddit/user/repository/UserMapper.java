package com.example.reddit.user.repository;

import com.example.reddit.mapper.source.userPost.UserPostInfoWithInteractions;
import com.example.reddit.mapper.source.userPost.UserPostInfoWithoutInteractions;
import java.time.Instant;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select(
    "SELECT u.id, u.created_at AS userCreatedAt," +
    " u.username, u.email, u.post_amounts, p.id AS postId," +
    " p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt," +
    " p.title, p.content, p.view_count, p.vote_points, p.like_points," +
    " p.confused_points, p.laugh_points, p.comment_amounts" +
    " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
    " WHERE p.user_id = #{userId} AND p.created_at < #{timeFrame}" + // userId & timeFrame
    " ORDER BY p.created_at DESC LIMIT #{fetchCountPlusOne} OFFSET #{offset}" // fetchCountPlusOne & offset
  )
  public List<UserPostInfoWithoutInteractions> getUserPostWithoutInteractions(
    @Param("offset") Integer offset,
    @Param("timeFrame") Instant timeFrame,
    @Param("fetchCountPlusOne") Integer fetchCountPlusOne,
    @Param("userId") Long userId
  );

  @Select(
    "SELECT u.id, u.created_at AS userCreatedAt, u.username, u.email," +
    " u.post_amounts, p.id AS postId, p.created_at AS postCreatedAt," +
    " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count, p.vote_points," +
    " p.like_points, p.confused_points, p.laugh_points, p.comment_amounts," +
    " i.vote_status, i.like_status, i.laugh_status, i.confused_status" +
    " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
    " LEFT JOIN interactions i ON i.post_id = p.id AND i.user_id = #{meId}" + // meId
    " WHERE p.user_id = #{userId} AND p.created_at < #{timeFrame}" + // userId & timeFrame
    " ORDER BY p.created_at DESC LIMIT #{fetchCountPlusOne} OFFSET #{offset}" // fetchCountPlusOne & offset)
  )
  public List<UserPostInfoWithInteractions> getUserPostWithInteractions(
    @Param("offset") Integer offset,
    @Param("timeFrame") Instant timeFrame,
    @Param("fetchCountPlusOne") Integer fetchCountPlusOne,
    @Param("userId") Long userId,
    @Param("meId") Long meId
  );
}
