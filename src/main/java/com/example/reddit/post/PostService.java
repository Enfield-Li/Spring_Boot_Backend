package com.example.reddit.post;

import com.example.reddit.mapper.HomePostMapper;
import com.example.reddit.mapper.source.homePost.PostInfoWithInteractions;
import com.example.reddit.mapper.source.homePost.PostInfoWithoutInteractions;
import com.example.reddit.mapper.target.homePost.PostAndInteractions;
import com.example.reddit.post.dto.request.CreatePostDto;
import com.example.reddit.post.dto.request.UpdatePostDto;
import com.example.reddit.post.dto.response.PaginatedPostsRO;
import com.example.reddit.post.entity.Post;
import com.example.reddit.user.UserRepository;
import com.example.reddit.user.entity.User;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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
    User author = userRepository
      .findById(userId)
      .orElseThrow(NoSuchElementException::new);

    // Author post amount + 1
    author.setPostAmounts(author.getPostAmounts() + 1);

    // Form post-user relationship
    Post post = Post.of(dto.getTitle(), dto.getContent(), author);
    postRepository.save(post);

    return post;
  }

  @Transactional
  public Post editPost(Long postId, UpdatePostDto dto, Long userId) {
    Post post = postRepository
      .findById(postId)
      .orElseThrow(NoSuchElementException::new);

    // @Transcational listen to state change-flush to db
    if (dto.getContent() != null) {
      post.setContent(dto.getContent());
    } else if (dto.getTitle() != null) {
      post.setTitle(dto.getTitle());
    }

    return post;
  }

  @Transactional
  public Boolean deletePost(Long postId, Long userId) {
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
  }

  @SuppressWarnings("unchecked")
  public PaginatedPostsRO fetchPaginatedPost(
    Long meId,
    Instant cursor,
    Integer take
  ) {
    Integer fetchCount = Math.min(takeAmount, 25);
    Integer fetchCountPlusOne = fetchCount + 1;
    Integer offset = cursor == null ? 0 : 1;
    Instant timeFrame = cursor == null ? Instant.now() : cursor;

    if (meId == null) {
      Query queryRes = em
        .createNativeQuery(
          "SELECT u.id, u.username, p.id AS postId," +
          " p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt," +
          " p.title, p.content, p.view_count, p.vote_points, p.like_points," +
          " p.confused_points, p.laugh_points, p.comment_amounts" +
          " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
          " WHERE p.created_at < :cursor" +
          " ORDER BY p.created_at DESC LIMIT :fetchCountPlusOne OFFSET :offset",
          "HomePostWithoutInteractions" // SQL to POJO
        )
        .setParameter("offset", offset)
        .setParameter("cursor", timeFrame)
        .setParameter("fetchCountPlusOne", fetchCountPlusOne);

      List<PostInfoWithoutInteractions> postList = (List<PostInfoWithoutInteractions>) queryRes.getResultList();

      return buildPaginatedPostsRO(postList, fetchCountPlusOne);
    }

    Query queryRes = em
      .createNativeQuery(
        "SELECT u.id, u.username, p.id AS postId, p.created_at AS postCreatedAt," +
        " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count," +
        " p.vote_points, p.like_points, p.confused_points, p.laugh_points," +
        " p.comment_amounts, i.vote_status, i.like_status, i.laugh_status, i.confused_status" +
        " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
        " LEFT JOIN interactions i ON i.post_id = p.id AND i.user_id = :meId" +
        " WHERE p.created_at < :cursor ORDER BY p.created_at DESC" +
        " LIMIT :fetchCountPlusOne OFFSET :offset",
        "HomePostWithInteractions" // SQL to POJO
      )
      .setParameter("meId", meId)
      .setParameter("offset", offset)
      .setParameter("cursor", timeFrame)
      .setParameter("fetchCountPlusOne", fetchCountPlusOne);

    List<PostInfoWithInteractions> postList = (List<PostInfoWithInteractions>) queryRes.getResultList();

    return buildPaginatedPostsROWithInteractions(postList, fetchCountPlusOne);
  }

  public PostAndInteractions fetchSinglePost(Long postId, Long meId) {
    if (meId == null) {
      Query queryRes = em
        .createNativeQuery(
          "SELECT u.id, u.username, p.id AS postId, p.created_at AS postCreatedAt," +
          " p.updated_at AS postUpdatedAt, p.title, p.content," +
          " p.view_count, p.vote_points, p.like_points," +
          " p.confused_points, p.laugh_points, p.comment_amounts" +
          " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
          " WHERE p.id = :postId",
          "HomePostWithoutInteractions" // SQL to POJO
        )
        .setParameter("postId", postId);

      PostInfoWithoutInteractions postList = (PostInfoWithoutInteractions) queryRes.getSingleResult();

      HomePostMapper mapper = Mappers.getMapper(HomePostMapper.class);
      return mapper.toPostAndInteractions(postList);
    }

    Query queryRes = em
      .createNativeQuery(
        "SELECT u.id, u.username, p.id AS postId, p.created_at AS postCreatedAt," +
        " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count," +
        " p.vote_points, p.like_points, p.confused_points, p.laugh_points," +
        " p.comment_amounts, i.vote_status, i.like_status, i.laugh_status, i.confused_status" +
        " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
        " LEFT JOIN interactions i ON i.post_id = p.id AND i.user_id = :meId" +
        " WHERE p.id = :postId",
        "HomePostWithInteractions" // SQL to POJO
      )
      .setParameter("meId", meId)
      .setParameter("postId", postId);

    PostInfoWithInteractions postList = (PostInfoWithInteractions) queryRes.getSingleResult();

    HomePostMapper mapper = Mappers.getMapper(HomePostMapper.class);
    return mapper.toPostAndInteractions(postList);
  }

  private PaginatedPostsRO buildPaginatedPostsRO(
    List<PostInfoWithoutInteractions> postList,
    Integer fetchCountPlusOne
  ) {
    Boolean hasMore = postList.size() == fetchCountPlusOne;

    if (hasMore) postList.remove(postList.size() - 1);

    HomePostMapper mapper = Mappers.getMapper(HomePostMapper.class);
    List<PostAndInteractions> postAndInteractionsList = new ArrayList<>();

    for (PostInfoWithoutInteractions sourceItem : postList) {
      // Slice content and only send 50 char
      String postContent = sourceItem.getContent();
      if (postContent != null && postContent.length() > 50) {
        String contentSnippet = postContent.substring(0, 50);
        sourceItem.setContent(contentSnippet);
      }

      PostAndInteractions dtoItem = mapper.toPostAndInteractions(sourceItem);

      postAndInteractionsList.add(dtoItem);
    }

    return new PaginatedPostsRO(hasMore, postAndInteractionsList);
  }

  private PaginatedPostsRO buildPaginatedPostsROWithInteractions(
    List<PostInfoWithInteractions> postList,
    Integer fetchCountPlusOne
  ) {
    Boolean hasMore = postList.size() == fetchCountPlusOne;

    if (hasMore) postList.remove(postList.size() - 1);

    HomePostMapper mapper = Mappers.getMapper(HomePostMapper.class);
    List<PostAndInteractions> postAndInteractionsList = new ArrayList<>();

    for (PostInfoWithInteractions sourceItem : postList) {
      // Slice content and only send 50 char
      String postContent = sourceItem.getContent();
      if (postContent != null && postContent.length() > 50) {
        String contentSnippet = postContent.substring(0, 50);
        sourceItem.setContent(contentSnippet);
      }

      PostAndInteractions dtoItem = mapper.toPostAndInteractions(sourceItem);

      postAndInteractionsList.add(dtoItem);
    }

    return new PaginatedPostsRO(hasMore, postAndInteractionsList);
  }
}
