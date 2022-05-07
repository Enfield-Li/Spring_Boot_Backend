package com.example.reddit.post;

import com.example.reddit.post.dto.query.PostWithInteractions;
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
  private final PostRepository postRepository;

  @Autowired
  PostController(PostService postService, PostRepository postRepository) {
    this.postService = postService;
    this.postRepository = postRepository;
  }

  @GetMapping("test")
  public Object test() {
    return postRepository.getPostWithInteractions(1L, 1L);
  }

  @GetMapping("test2")
  public Post test2() {
    return postRepository.findById(1L).orElseThrow();
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
  public List<Post> getPaginatedPosts() {
    return postService.fetchPaginatedPost();
  }

  @GetMapping("paginated-posts/top")
  public List<Post> getPaginatedPostsByTop() {
    return postService.fetchPaginatedPost();
  }
}
