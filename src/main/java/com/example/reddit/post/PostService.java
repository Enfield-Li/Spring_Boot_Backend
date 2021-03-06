package com.example.reddit.post;

import com.example.reddit.interactions.entity.CompositeKeys;
import com.example.reddit.interactions.entity.Interactions;
import com.example.reddit.mapper.HomePostMapper;
import com.example.reddit.mapper.source.homePost.PostInfoWithInteractions;
import com.example.reddit.mapper.source.homePost.PostInfoWithoutInteractions;
import com.example.reddit.mapper.target.homePost.HomePost;
import com.example.reddit.mapper.target.homePost.PostAndInteractions;
import com.example.reddit.mapper.target.userPost.AuthorInfo;
import com.example.reddit.post.dto.dbProjection.PostAuthorInfo;
import com.example.reddit.post.dto.request.CreatePostDto;
import com.example.reddit.post.dto.request.UpdatePostDto;
import com.example.reddit.post.dto.response.PaginatedPostsRO;
import com.example.reddit.post.entity.Post;
import com.example.reddit.post.repository.PostMapper;
import com.example.reddit.post.repository.PostRepository;
import com.example.reddit.user.UserService;
import com.example.reddit.user.entity.User;
import com.example.reddit.user.repository.UserRepository;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private static final Integer defaultAmount = 10;
  private final PostRepository postRepo;
  private final UserRepository userRepo;
  private final PostMapper postMapper;
  private final UserService userService;

  @Autowired
  PostService(
    PostRepository postRepository,
    UserRepository userRepository,
    PostMapper postMapper,
    UserService userService
  ) {
    this.postRepo = postRepository;
    this.userRepo = userRepository;
    this.postMapper = postMapper;
    this.userService = userService;
  }

  @Transactional
  public PostAndInteractions createPost(CreatePostDto dto, Long userId) {
    User author = userRepo
      .findById(userId)
      .orElseThrow(NoSuchElementException::new);

    /* 
      ?????????????????? + 1
      Author post amount + 1
     */
    author.setPostAmounts(author.getPostAmounts() + 1);

    /*
       ??????????????????????????????
       Form post-user relationship 
     */
    Post post = Post.of(dto.getTitle(), dto.getContent(), author);
    post.setVotePoints(1);
    Post createdPost = postRepo.save(post);

    /* 
      ??????????????????????????????????????????
      Author new interactions with created post, casting upvote and like
     */
    Interactions interactions = Interactions.ofPostCreation(
      CompositeKeys.of(userId, post.getId())
    );
    createdPost.setInteractions(Arrays.asList(interactions));

    return builPostAndInteractions(author, createdPost, userId, interactions);
  }

  @Transactional
  public Post editPost(Long postId, UpdatePostDto dto, Long userId) {
    Post post = postRepo
      .findById(postId)
      .orElseThrow(NoSuchElementException::new);

    if (!post.getUserId().equals(userId)) return null;

    if (dto.getContent() != null) post.setContent(dto.getContent());
    if (dto.getTitle() != null) post.setTitle(dto.getTitle());

    return post;
  }

  @Transactional
  public Boolean deletePost(Long postId, Long userId) {
    PostAuthorInfo postAuthorInfo = postRepo
      .getUserIdByid(postId)
      .orElseThrow(NoSuchElementException::new);

    /* 
      ?????????????????????????????????
      If it wasn't the author, do nothing
     */
    Boolean isAuthor = postAuthorInfo.getUserId().equals(userId);
    if (!isAuthor) return false;

    /* 
    ???????????????????????????????????? - 1
    Author post amount - 1 after post been deleted
    */
    postRepo.deleteById(postId);
    userRepo.authorPostAmountMinusOne(userId);

    return true;
  }

  /* 
    ?????????????????????
    Cursor based pagination
   */
  public PaginatedPostsRO fetchPaginatedPost(
    Long meId,
    Instant cursor,
    Integer take,
    String sortBy
  ) {
    /* 
      ??????????????????
      Setting up default params
     */
    Integer takeAmount = take == null ? defaultAmount : take; // ????????????10??? Default fetch amount: 10
    Integer fetchAmount = Math.min(takeAmount, 25);
    Integer fetchAmountPlusOne = fetchAmount + 1;

    Integer offset = cursor == null ? 0 : 1;
    Instant timeFrame = cursor == null ? Instant.now() : cursor;

    /* 
      ?????? sortBy ???????????????
      Set up filter criteria according to sortBy
     */
    String dateSpec = null;
    Integer votePointsLowest = null;
    Integer laughPointsLowest = null;
    Integer likePointsLowest = null;

    if (sortBy.equals("best")) {
      dateSpec = this.daysBefore(60);
      votePointsLowest = 30;
      laughPointsLowest = 20;
    }
    if (sortBy.equals("hot")) {
      dateSpec = this.daysBefore(30);
      likePointsLowest = 20;
    }

    /* 
      ?????????????????????????????????????????????????????????
      User not loged in, therefore fetch posts without interactions
     */
    if (meId == null) {
      List<PostInfoWithoutInteractions> postList = postMapper.getPatinatedPostsWithoutInteractions(
        offset,
        timeFrame,
        fetchAmountPlusOne,
        dateSpec,
        votePointsLowest,
        laughPointsLowest,
        likePointsLowest
      );

      return buildPaginatedPostsRO(postList, fetchAmountPlusOne);
    }

    /* 
      ??????????????????????????????????????????????????????
      User loged in, therefore fetch posts with interactions
     */
    List<PostInfoWithInteractions> postList = postMapper.getPatinatedPostsWithInteractions(
      meId,
      offset,
      timeFrame,
      fetchAmountPlusOne,
      dateSpec,
      votePointsLowest,
      laughPointsLowest,
      likePointsLowest
    );

    return buildPaginatedPostsRO(postList, fetchAmountPlusOne);
  }

  public PostAndInteractions fetchSinglePost(Long postId, Long meId) {
    /* 
      ?????????????????????????????????????????????????????????
      User not loged in, therefore fetch post without interactions
     */
    if (meId == null) {
      PostInfoWithoutInteractions postList = postMapper.getPostInfoWithoutInteractions(
        postId
      );

      // ??? POJO ????????????????????????Map POJO to response object???
      HomePostMapper mapper = Mappers.getMapper(HomePostMapper.class);
      return mapper.toPostAndInteractions(postList);
    }

    /* 
      ??????????????????????????????????????????????????????
      User loged in, therefore fetch post with interactions
     */
    PostInfoWithInteractions postList = postMapper.getPostInfoWithInteractions(
      postId,
      meId
    );

    // ??? POJO ????????????????????????Map POJO to response object???
    HomePostMapper mapper = Mappers.getMapper(HomePostMapper.class);
    return mapper.toPostAndInteractions(postList);
  }

  private <T extends PostInfoWithoutInteractions> PaginatedPostsRO buildPaginatedPostsRO(
    List<T> postList,
    Integer fetchCountPlusOne
  ) {
    Boolean hasMore = postList.size() == fetchCountPlusOne;

    if (hasMore) postList.remove(postList.size() - 1);

    HomePostMapper mapper = Mappers.getMapper(HomePostMapper.class);

    List<PostAndInteractions> postAndInteractionsList = new ArrayList<>();

    postList.forEach(
      post -> {
        String postContent = post.getContent();

        // ?????????????????????50?????????(Slice post content and only send 50 char)
        post.setContent(userService.sliceContent(postContent));

        if (post.getClass().equals(PostInfoWithInteractions.class)) {
          PostAndInteractions postDto = mapper.toPostAndInteractions(
            (PostInfoWithInteractions) post
          );

          postAndInteractionsList.add(postDto);
          return;
        }

        PostAndInteractions postDto = mapper.toPostAndInteractions(post);

        postAndInteractionsList.add(postDto);
      }
    );

    return new PaginatedPostsRO(hasMore, postAndInteractionsList);
  }

  private String daysBefore(Integer days) {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    .format(Date.from(Instant.now().minus(Duration.ofDays(days))));
  }

  private PostAndInteractions builPostAndInteractions(
    User author,
    Post createdPost,
    Long userId,
    Interactions interactions
  ) {
    AuthorInfo authorRes = new AuthorInfo(author.getId(), author.getUsername());
    HomePost postRes = new HomePost(
      createdPost.getId(),
      createdPost.getCreatedAt(),
      createdPost.getUpdatedAt(),
      createdPost.getTitle(),
      createdPost.getContent(),
      createdPost.getViewCount(),
      createdPost.getVotePoints(),
      createdPost.getLikePoints(),
      createdPost.getConfusedPoints(),
      createdPost.getLaughPoints(),
      createdPost.getCommentAmounts(),
      userId,
      authorRes
    );
    // class?????? / Repeated class naming
    com.example.reddit.mapper.target.Interactions inRes = new com.example.reddit.mapper.target.Interactions(
      interactions.getVoteStatus(),
      interactions.getLikeStatus(),
      interactions.getLaughStatus(),
      interactions.getConfusedStatus()
    );

    return new PostAndInteractions(postRes, inRes);
  }
}
