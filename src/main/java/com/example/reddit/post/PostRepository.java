package com.example.reddit.post;

import com.example.reddit.post.dto.dbProjection.PostAuthorInfo;
import com.example.reddit.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  Optional<PostAuthorInfo> getUserIdByid(Long id);
}
