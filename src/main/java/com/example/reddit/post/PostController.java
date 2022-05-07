package com.example.reddit.post;

import com.example.reddit.post.dto.request.CreatePostDto;
import com.example.reddit.post.dto.request.UpdatePostDto;
import com.example.reddit.post.entity.Post;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post")
@RestController
@RequestMapping("post")
class PostController {

  private final PostService postService;

  @Autowired
  PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public List<Post> getAll(HttpSession session) {
    return postService.fetchPaginatedPost();
  }

  @GetMapping("single-post/{id}")
  public Post getById(@PathVariable("id") Long id) {
    return postService.fetchSinglePost(id);
  }

  @PostMapping("create-post")
  public ResponseEntity<Post> create(
    @RequestBody CreatePostDto dto,
    HttpSession session
  ) {
    try {
      Post post = postService.createPost(
        dto,
        (Long) session.getAttribute("userId")
      );
      return new ResponseEntity<>(post, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("edit/{id}")
  public Post update(
    @PathVariable("id") Long id,
    @RequestBody UpdatePostDto dto,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");

    if (userId == null) return null;

    return postService.editPost(id, dto, userId);
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }

  @GetMapping("search-post")
  public ResponseEntity<List<Post>> searchPosts(@PathVariable("id") Long id) {
    return null;
  }

  @GetMapping("paginated-posts")
  public ResponseEntity<List<Post>> getPaginatedPosts() {
    return null;
  }

  @GetMapping("paginated-posts/top")
  public ResponseEntity<List<Post>> getPaginatedPostsByTop() {
    return null;
  }
}
