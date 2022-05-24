package com.example.reddit.interactions.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InteractionsMapper {
  @Insert(
    "INSERT INTO interactions" +
    " (post_id, user_id, ${fieldStatus})" +
    " VALUES (#{postId}, #{meId}, #{boolValue})"
  )
  public Integer createInteractions(
    @Param("fieldStatus") String fieldStatStr,
    @Param("meId") Long meId,
    @Param("postId") Long postId,
    @Param("boolValue") Boolean boolValue
  );

  @Update(
    "UPDATE post" +
    " SET ${fieldPoints} = IFNULL(${fieldPoints}, 0) + #{intValue}" +
    " WHERE id = #{postId}"
  )
  public Integer updateFieldPoints(
    @Param("fieldPoints") String fieldPointsStr,
    @Param("postId") Long postId,
    @Param("intValue") Integer intValue
  );

  @Update(
    "UPDATE interactions" +
    " SET ${fieldStatStr} = #{boolValue}" +
    " WHERE post_id = #{postId}" +
    " AND user_id = #{meId}"
  )
  public Integer updateFieldStatus(
    @Param("fieldStatStr") String fieldStatStr,
    @Param("meId") Long meId,
    @Param("postId") Long postId,
    @Param("boolValue") Boolean boolValue
  );
}
