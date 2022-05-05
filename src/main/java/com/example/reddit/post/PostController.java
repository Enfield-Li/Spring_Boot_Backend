package com.example.reddit.post;

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

  @Autowired
  PostRepository postRepository;

  @GetMapping
  public List<Post> getAll() {
    return postRepository.findAll();
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
    session.setAttribute("userId", num);
  }

  @GetMapping("getsession")
  public void getSession(HttpSession session, HttpServletRequest request) {
    // System.out.println(request.getSession().getAttribute("userId"));
    System.out.println(session.getAttribute("userId"));
  }

  @GetMapping("{id}")
  public ResponseEntity<Post> getById(@PathVariable("id") Long id) {
    Optional<Post> existingItemOptional = postRepository.findById(id);

    if (existingItemOptional.isPresent()) {
      return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Post> create(@RequestBody Post item) {
    try {
      Post savedItem = postRepository.save(item);
      return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Post> update(
    @PathVariable("id") Long id,
    @RequestBody Post item
  ) {
    Optional<Post> existingItemOptional = postRepository.findById(id);
    if (existingItemOptional.isPresent()) {
      Post existingItem = existingItemOptional.get();
      System.out.println(
        "TODO for developer - update logic is unique to entity and must be implemented manually."
      );
      //existingItem.setSomeField(item.getSomeField());
      return new ResponseEntity<>(
        postRepository.save(existingItem),
        HttpStatus.OK
      );
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
    try {
      postRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
