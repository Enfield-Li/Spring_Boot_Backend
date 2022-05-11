package com.example.reddit.post;

import com.example.reddit.post.dto.classes.PostInfo;
import com.example.reddit.post.dto.classes.PostMoreInfo;
import com.example.reddit.post.dto.classes.PostTitle;
import com.example.reddit.post.dto.classes.PostWithUser;
import com.example.reddit.post.dto.classes.PostWithUserInteractions;
import com.example.reddit.post.dto.interfaces.PostWithAuthorAndInteractions;
import com.example.reddit.post.dto.request.CreatePostDto;
import com.example.reddit.post.dto.request.UpdatePostDto;
import com.example.reddit.post.entity.Post;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post")
@RestController
@RequestMapping("post")
class PostController {

  private final PostService postService;
  private final PostRepository postRepository;
  private final EntityManager em;

  @Autowired
  PostController(
    PostService postService,
    PostRepository postRepository,
    EntityManager em
  ) {
    this.postService = postService;
    this.postRepository = postRepository;
    this.em = em;
  }

  @GetMapping("test")
  public PostWithUserInteractions test() {
    Query q = em.createNativeQuery(
      "SELECT p.id, p.title, u.username, i.vote_status AS voteStatus FROM post p JOIN user u on p.user_id = u.id JOIN interactions i on p.id = i.post_id AND u.id = i.user_id WHERE p.id = 1 AND u.id = 1;",
      "PostTitle"
    );
    // PostWithUserInteractions res = (PostWithUserInteractions) q.getSingleResult();
    // return res;
    return (PostWithUserInteractions) q.getSingleResult();
  }

  @GetMapping("test2")
  public PostWithUserInteractions getOne() {
    return postRepository.getPostTitle();
  }

  @GetMapping
  public Object getAll(HttpSession session) {
    return postService.fetchPaginatedPost(1L, null, null);
  }

  @GetMapping("single-post/{id}")
  public Post getById(@PathVariable("id") Long id) {
    return postService.fetchSinglePost(id);
  }

  @PostMapping("create-post")
  public ResponseEntity create(
    @RequestBody CreatePostDto dto,
    HttpSession session
  ) {
    try {
      Long userId = (Long) session.getAttribute("userId");
      if (userId == null) {
        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("You'll have to login first :)");
      }

      Post post = postService.createPost(dto, userId);

      return ResponseEntity.status(HttpStatus.CREATED).body(post);
    } catch (DataIntegrityViolationException e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Oops, post title already taken!");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @PutMapping("edit/{id}")
  public ResponseEntity update(
    @PathVariable("id") Long id,
    @RequestBody UpdatePostDto dto,
    HttpSession session
  ) {
    try {
      Long userId = (Long) session.getAttribute("userId");
      if (userId == null) {
        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("You'll have to login first :)");
      }

      Post post = postService.editPost(id, dto, userId);

      return ResponseEntity.status(HttpStatus.CREATED).body(post);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity delete(
    @PathVariable("id") Long id,
    HttpSession session
  ) {
    try {
      Long userId = (Long) session.getAttribute("userId");
      if (userId == null) {
        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("You'll have to login first :)");
      }

      Boolean deleted = postService.deletePost(id, userId);
      if (!deleted) throw new Exception();

      return ResponseEntity.status(HttpStatus.CREATED).body(deleted);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @GetMapping("search-post")
  public ResponseEntity<List<Post>> searchPosts(@PathVariable("id") Long id) {
    return null;
  }

  @GetMapping("paginated-posts")
  public List<Post> getPaginatedPosts(
    HttpSession session,
    @RequestParam(
      name = "cursor",
      required = false
    ) @DateTimeFormat Instant cursor,
    @RequestParam(name = "take", required = false) Integer take
  ) {
    Long meId = (Long) session.getAttribute("userId");

    return null;
    // return postService.fetchPaginatedPost(meId, cursor, take);
  }

  @GetMapping("paginated-posts/top")
  public List<Post> getPaginatedPostsByTop(
    HttpSession session,
    @RequestParam(
      name = "cursor",
      required = false
    ) @DateTimeFormat Instant cursor,
    @RequestParam(name = "take", required = false) Integer take
  ) {
    Long meId = (Long) session.getAttribute("userId");

    return null;
    // return postService.fetchPaginatedPost(meId, cursor, take);
  }
}
