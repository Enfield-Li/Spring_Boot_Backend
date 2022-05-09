package com.example.reddit.post;

import com.example.reddit.post.dto.classes.PostInfo;
import com.example.reddit.post.dto.classes.PostMoreInfo;
import com.example.reddit.post.dto.classes.PostTitle;
import com.example.reddit.post.dto.interfaces.PostWithAuthorAndInteractions;
import com.example.reddit.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  @Query(
    value = "SELECT p.title, p.id" +
    " FROM post p" +
    " WHERE i.post_id = 1 AND i.user_id = 2",
    nativeQuery = true
  )
  Post getPostWithInteractions(
    // @Param("postId") Long postId,
    // @Param("userId") Long userId
  );

  @Query(nativeQuery = true)
  PostInfo getPostTitle();

  @Query(
    value = "SELECT p.id, p.created_at, p.updated_at, p.title, p.content, p.user_id," +
    " u.username" +
    " FROM post p" +
    " JOIN user u" +
    " ON p.user_id = u.id" +
    " WHERE p.id = 2",
    nativeQuery = true
  )
  Object[] getPostLimited();

  @Query(
    value = "SELECT p.id, p.created_at, p.updated_at, p.title, p.content, p.user_id," +
    " u.id as userId, u.username" +
    " FROM post p" +
    " JOIN user u" +
    " ON p.user_id = u.id",
    nativeQuery = true
  )
  List<PostWithAuthorAndInteractions> getPostLimitedList();
}
