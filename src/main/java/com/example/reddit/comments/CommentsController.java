package com.example.reddit.comments;

import com.example.reddit.comments.entity.Comments;
import com.example.reddit.comments.repository.CommentsRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

@RestController
@RequestMapping("/comments")
class CommentsController {

  private final CommentsRepository commentsRepository;
  private final CommentsService commentsService;

  @Autowired
  CommentsController(
    CommentsRepository repository,
    CommentsService commentsService
  ) {
    this.commentsRepository = repository;
    this.commentsService = commentsService;
  }

  @GetMapping("test")
  public void test() {}

  @GetMapping
  public ResponseEntity<?> getAll() {
    return null;
  }

  @GetMapping("{id}")
  public ResponseEntity<?> getById(@PathVariable("id") Long id) {
    return null;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Comments item) {
    return null;
  }

  @PutMapping("{id}")
  public ResponseEntity<?> update(
    @PathVariable("id") Long id,
    @RequestBody Comments item
  ) {
    return null;
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    return null;
  }
}
