package com.example.reddit.post;

import com.example.reddit.post.dto.request.CreatePostDto;
import com.example.reddit.post.dto.request.UpdatePostDto;
import com.example.reddit.post.entity.Post;
import com.example.reddit.user.UserRepository;
import com.example.reddit.user.entity.User;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final EntityManager em;
  private Integer takeAmount = 10;

  @Autowired
  PostService(
    PostRepository postRepository,
    UserRepository userRepository,
    EntityManager em
  ) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.em = em;
  }

  @Transactional
  public Post createPost(CreatePostDto dto, Long userId) {
    try {
      User author = userRepository
        .findById(userId)
        .orElseThrow(NoSuchElementException::new);

      // Author post amount + 1
      author.setPostAmounts(author.getPostAmounts() + 1);

      Post post = Post.of(dto.getTitle(), dto.getContent(), author);
      postRepository.save(post);

      return post;
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  @Transactional
  public Post editPost(Long postId, UpdatePostDto dto, Long userId) {
    try {
      Post post = postRepository
        .findById(postId)
        .orElseThrow(NoSuchElementException::new);

      if (dto.getContent() != null) {
        post.setContent(dto.getContent());
      } else if (dto.getTitle() != null) {
        post.setTitle(dto.getTitle());
      }

      return post;
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  @Transactional
  public Boolean deletePost(Long postId, Long userId) {
    try {
      Post post = postRepository
        .findById(postId)
        .orElseThrow(NoSuchElementException::new);

      // If it wasn't the author, do nothing
      if (post.getUserId().equals(userId)) return true;

      postRepository.delete(post);

      // Author post amount - 1
      User author = userRepository
        .findById(post.getUserId())
        .orElseThrow(NoSuchElementException::new);
      author.setPostAmounts(author.getPostAmounts() - 1);

      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public List<Object> fetchPaginatedPost(
    Long meId,
    Instant cursor,
    Integer take
  ) {
    Integer fetchCount = Math.min(takeAmount, 25);
    Integer fetchCountPlusOne = fetchCount + 1;
    Integer offset = cursor == null ? 0 : 1;
    Instant timeFrame = cursor == null ? Instant.now() : cursor;

    if (meId == null) {
      Query queryResWithoutInteraction = em
        .createNativeQuery(
          "SELECT u.id, u.created_at AS userCreatedAt," +
          " u.username, u.email, u.post_amounts, p.id AS postId," +
          " p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt," +
          " p.title, p.content, p.view_count, p.vote_points, p.like_points," +
          " p.confused_points, p.laugh_points, p.comment_amounts" +
          " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
          " AND p.created_at < :cursor" +
          " ORDER BY p.created_at DESC LIMIT :fetchCountPlusOne OFFSET :offset",
          "HomeProfileWithoutInteractions"
        )
        .setParameter("offset", offset)
        .setParameter("cursor", timeFrame)
        .setParameter("fetchCountPlusOne", fetchCountPlusOne);

      return queryResWithoutInteraction.getResultList();
    }

    Query queryResWithoutInteraction = em
      .createNativeQuery(
        "SELECT u.id, u.created_at AS userCreatedAt, u.username, u.email," +
        " u.post_amounts, p.id AS postId, p.created_at AS postCreatedAt," +
        " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count," +
        " p.vote_points, p.like_points, p.confused_points, p.laugh_points," +
        " p.comment_amounts, i.created_at AS interactionCreatedAt," +
        " i.updated_at AS interactionUpdatedAt, i.vote_status, i.like_status," +
        " i.laugh_status, i.confused_status, i.have_read, i.have_checked" +
        " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
        " LEFT JOIN interactions i ON i.post_id = p.id" +
        " AND i.user_id = :meId WHERE p.created_at < :cursor" +
        " ORDER BY p.created_at DESC LIMIT :fetchCountPlusOne OFFSET :offset",
        "HomeProfileWithInteractions"
      )
      .setParameter("meId", meId)
      .setParameter("offset", offset)
      .setParameter("cursor", timeFrame)
      .setParameter("fetchCountPlusOne", fetchCountPlusOne);

    return queryResWithoutInteraction.getResultList();
  }

  public Post fetchSinglePost(Long id) {
    return postRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }
}
