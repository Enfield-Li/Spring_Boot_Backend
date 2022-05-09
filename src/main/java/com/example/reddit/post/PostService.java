package com.example.reddit.post;

import com.example.reddit.post.dto.request.CreatePostDto;
import com.example.reddit.post.dto.request.UpdatePostDto;
import com.example.reddit.post.entity.Post;
import com.example.reddit.user.UserRepository;
import com.example.reddit.user.entity.User;
import java.util.List;
import java.util.NoSuchElementException;
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

  @Autowired
  PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public Post createPost(CreatePostDto dto, Long userId) {
    try {
      User author = userRepository.findById(userId).orElseThrow();

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
      Post post = postRepository.findById(postId).orElseThrow();

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
      Post post = postRepository.findById(postId).orElseThrow();

      // If it wasn't the author, do nothing
      if (post.getUserId().equals(userId)) return true;

      postRepository.delete(post);

      // Author post amount - 1
      User author = userRepository.findById(post.getUserId()).orElseThrow();
      author.setPostAmounts(author.getPostAmounts() - 1);

      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public List<Post> fetchPaginatedPost() {
    return postRepository.findAll();
  }

  public Post fetchSinglePost(Long id) {
    return postRepository.findById(id).orElseThrow();
  }
}
