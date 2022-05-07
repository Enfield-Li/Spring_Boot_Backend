package com.example.reddit.post;

import com.example.reddit.post.dto.query.PostWithInteractions;
import com.example.reddit.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  @Query(
    value = "SELECT p.*" +
    " FROM post p WHERE p.id = :postId AND p.user_id = :userId",
    nativeQuery = true
  )
  // @Query("SELECT new com.example.reddit.post.dto.query.PostWithInteractions(*) FROM post WHERE ")
  Post getPostWithInteractions(
      @Param("postId") Long postId,
      @Param("userId") Long userId
  );
}
