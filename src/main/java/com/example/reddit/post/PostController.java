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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post")
@RestController
@RequestMapping("/post")
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

  @GetMapping("{id}")
  public ResponseEntity<Post> getById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
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

  @PutMapping("{id}")
  public Post update(
    @PathVariable("id") Long id,
    @RequestBody UpdatePostDto dto,
    HttpSession session
  ) {
    Long userId = (Long) session.getAttribute("userId");

    if (userId == null) return null;

    return postService.editPost(id, dto, userId);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    return null;
  }

  @GetMapping("setsession")
  public void setSession(
    HttpSession session,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    Random rand = new Random();

    // Obtain a number between [0 - 49].
    Integer num = rand.nextInt(50);

    // request.getSession().setAttribute("userId", 11);
    session.setAttribute("userId", 44);
  }

  @GetMapping("getsession")
  public void getSession(HttpSession session, HttpServletRequest request) {
    // System.out.println(request.getSession().getAttribute("userId"));
    System.out.println(session.getAttribute("userId"));
  }
}
