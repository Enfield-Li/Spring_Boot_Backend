package com.example.reddit.post.repository;

import com.example.reddit.mapper.source.homePost.PostInfoWithInteractions;
import com.example.reddit.mapper.source.homePost.PostInfoWithoutInteractions;
import java.time.Instant;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostMapper {
  @Select(
    "SELECT u.id, u.username, p.id AS postId, p.created_at AS postCreatedAt," + // postId
    " p.updated_at AS postUpdatedAt, p.title, p.content," +
    " p.view_count, p.vote_points, p.like_points," +
    " p.confused_points, p.laugh_points, p.comment_amounts" +
    " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
    " WHERE p.id = #{postId}" // postId
  )
  public PostInfoWithoutInteractions getPostInfoWithoutInteractions(
    @Param("postId") Long postId
  );

  @Select(
    "SELECT u.id, u.username, p.id AS postId, p.created_at AS postCreatedAt," + // postId
    " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count," +
    " p.vote_points, p.like_points, p.confused_points, p.laugh_points," +
    " p.comment_amounts, i.vote_status, i.like_status, i.laugh_status, i.confused_status" +
    " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
    " LEFT JOIN interactions i ON i.post_id = p.id AND i.user_id = #{meId}" + // meId
    " WHERE p.id = #{postId}"
  )
  public PostInfoWithInteractions getPostInfoWithInteractions(
    @Param("postId") Long postId,
    @Param("meId") Long meId
  );

  public List<PostInfoWithoutInteractions> getPatinatedPostsWithoutInteractions(
    @Param("offset") Integer offset,
    @Param("timeFrame") Instant timeFrame,
    @Param("fetchAmountPlusOne") Integer fetchAmountPlusOne,
    @Param("dateSpec") String dateSpec,
    @Param("votePointsLowest") Integer votePointsLowest,
    @Param("laughPointsLowest") Integer laughPointsLowest,
    @Param("likePointsLowest") Integer likePointsLowest
  );

  public List<PostInfoWithInteractions> getPatinatedPostsWithInteractions(
    @Param("meId") Long meId,
    @Param("offset") Integer offset,
    @Param("timeFrame") Instant timeFrame,
    @Param("fetchAmountPlusOne") Integer fetchAmountPlusOne,
    @Param("dateSpec") String dateSpec,
    @Param("votePointsLowest") Integer votePointsLowest,
    @Param("laughPointsLowest") Integer laughPointsLowest,
    @Param("likePointsLowest") Integer likePointsLowest
  );
}
