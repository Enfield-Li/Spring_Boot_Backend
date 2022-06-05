package com.example.reddit.post;

import com.example.reddit.mapper.target.homePost.PostAndInteractions;
import com.example.reddit.post.dto.request.CreatePostDto;
import com.example.reddit.post.dto.request.UpdatePostDto;
import com.example.reddit.post.dto.response.PaginatedPostsRO;
import com.example.reddit.post.entity.Post;
import com.example.reddit.post.repository.PostRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post")
@RestController
@RequestMapping("post")
// @CrossOrigin(origins = "http://localhost:3118", maxAge = 3600)
class PostController {

  private final PostService postService;
  private final PostRepository postRepository;
  private static final Logger log = LoggerFactory.getLogger(
    PostController.class
  );

  @Autowired
  PostController(PostService postService, PostRepository postRepository) {
    this.postService = postService;
    this.postRepository = postRepository;
  }

  @GetMapping("single-post/{id}")
  public ResponseEntity<?> getById(
    @PathVariable("id") Long id,
    HttpSession session
  ) {
    try {
      Long meId = (Long) session.getAttribute("userId");

      PostAndInteractions res = postService.fetchSinglePost(id, meId);

      return ResponseEntity.status(HttpStatus.CREATED).body(res);
    } catch (NoResultException e) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Post does not exist no more...");
    }
  }

  @PostMapping("create-post")
  public ResponseEntity<?> create(
    @RequestBody CreatePostDto dto,
    HttpSession session
  ) {
    try {
      Long meId = (Long) session.getAttribute("userId");
      if (meId == null) {
        return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body("You'll have to login first :)");
      }

      PostAndInteractions post = postService.createPost(dto, meId);

      return ResponseEntity.status(HttpStatus.CREATED).body(post);
    } catch (DataIntegrityViolationException e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Oops, post title already taken!");
    }
  }

  @PutMapping("edit/{id}")
  public ResponseEntity<?> update(
    @PathVariable("id") Long id,
    @RequestBody UpdatePostDto dto,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("You'll have to login first :)");
    }

    Post post = postService.editPost(id, dto, userId);

    return ResponseEntity.status(HttpStatus.CREATED).body(post);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<?> delete(
    @PathVariable("id") Long id,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("You'll have to login first :)");
    }

    Boolean deleted = postService.deletePost(id, userId);

    return ResponseEntity.status(HttpStatus.CREATED).body(deleted);
  }

  @GetMapping("search-post")
  public ResponseEntity<?> searchPosts(@PathVariable("id") Long id) {
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
  }

  @GetMapping("paginated-posts")
  @Parameter(
    name = "sortBy",
    schema = @Schema(
      type = "string",
      allowableValues = { "best", "hot", "new" }
    ),
    required = true
  )
  public ResponseEntity<?> getPaginatedPosts(
    HttpSession session,
    @RequestParam(
      name = "cursor",
      required = false
    ) @DateTimeFormat Instant cursor,
    @RequestParam(name = "take", required = false) Integer take,
    String sortBy
  ) {
    Long meId = (Long) session.getAttribute("userId");

    PaginatedPostsRO res = postService.fetchPaginatedPost(
      meId,
      cursor,
      take,
      sortBy
    );
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
  }

  @GetMapping("paginated-posts/top")
  public ResponseEntity<?> getPaginatedPostsByTop(
    HttpSession session,
    @RequestParam(
      name = "cursor",
      required = false
    ) @DateTimeFormat Instant cursor,
    @RequestParam(name = "take", required = false) Integer take
  ) {
    Long meId = (Long) session.getAttribute("userId");

    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    // PaginatedPostsRO res = postService.fetchPaginatedPost(meId, cursor, take);
  }
}
