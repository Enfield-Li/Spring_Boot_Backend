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
import com.example.reddit.post.dto.dbProjection.PostInEdit;
import com.example.reddit.post.dto.request.CreatePostDto;
import com.example.reddit.post.dto.request.UpdatePostDto;
import com.example.reddit.post.dto.response.PaginatedPostsRO;
import com.example.reddit.post.entity.Post;
import com.example.reddit.post.repository.PostRepository;
import com.example.reddit.post.repository.dao.PostMapper;
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
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepo;
  private final UserRepository userRepo;
  private final PostMapper postMapper;
  private final EntityManager em;

  @Autowired
  PostService(
    PostRepository postRepository,
    UserRepository userRepository,
    PostMapper postMapper,
    EntityManager em
  ) {
    this.postRepo = postRepository;
    this.userRepo = userRepository;
    this.postMapper = postMapper;
    this.em = em;
  }

  @Transactional
  public PostAndInteractions createPost(CreatePostDto dto, Long userId) {
    User author = userRepo
      .findById(userId)
      .orElseThrow(NoSuchElementException::new);

    /* 
      作者帖子数量 + 1
      Author post amount + 1
     */
    author.setPostAmounts(author.getPostAmounts() + 1);

    /*
       绑定帖子与用户的关系
       Form post-user relationship 
     */
    Post post = Post.of(dto.getTitle(), dto.getContent(), author);
    post.setVotePoints(1);
    Post createdPost = postRepo.save(post);

    /* 
      创建新的互动——即投票和点赞
      Author casting upvote and like
     */
    Interactions interactions = Interactions.ofCreation(
      CompositeKeys.of(userId, post.getId())
    );
    createdPost.setInteractions(Arrays.asList(interactions));

    /* 
      Mapper 未配置
      Mapper unconfigured
     */
    // SinglePostMapper mapper = Mappers.getMapper(SinglePostMapper.class);
    // return mapper.toPostAndInteractions(createdPost);

    /* 
      构筑响应对象
      Build response object
    */
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
    // class重名 / Repeated class naming
    com.example.reddit.mapper.target.Interactions inRes = new com.example.reddit.mapper.target.Interactions(
      interactions.getVoteStatus(),
      interactions.getLikeStatus(),
      interactions.getLaughStatus(),
      interactions.getConfusedStatus()
    );

    return new PostAndInteractions(postRes, inRes);
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
      如果不是作者，不作变化
      If it wasn't the author, do nothing
     */
    if (!postAuthorInfo.getUserId().equals(userId)) return false;

    /* 
    删除成功后，作者帖子数量 - 1
    Author post amount - 1 after post been deleted
    */
    postRepo.deleteById(postId);
    userRepo.userPostAmountMinusOne(userId);

    return true;
  }

  /* 
    基于游标的分页
    Cursor based pagination
   */
  @SuppressWarnings("unchecked")
  public PaginatedPostsRO fetchPaginatedPost(
    Long meId,
    Instant cursor,
    Integer take,
    String sortBy
  ) {
    /* 
      设置基础参数
      Setting up default params
     */
    Integer takeAmount = take == null ? 10 : take; // 默认获取10条 Default fetch amount: 10
    Integer fetchAmount = Math.min(takeAmount, 25);
    Integer fetchAmountPlusOne = fetchAmount + 1;

    Integer offset = cursor == null ? 0 : 1;
    Instant timeFrame = cursor == null ? Instant.now() : cursor;

    /* 
      根据 sortBy 设置筛选项
      Set up filter criteria according to sortBy
     */
    String dateBeforeCondition, votePointsCondition, laughPointsCondition, likePointsCondition;
    String dateBefore = null;
    Integer votePoints = null;
    Integer laughPoints = null;
    Integer likePoints = null;

    if (sortBy.equals("best")) {
      dateBefore = this.daysBefore(60);
      votePoints = 30;
      laughPoints = 20;
    }
    if (sortBy.equals("hot")) {
      dateBefore = this.daysBefore(30);
      likePoints = 20;
    }

    dateBeforeCondition =
      dateBefore == null ? "" : " AND p.created_at > '" + dateBefore + "'";
    votePointsCondition =
      votePoints == null ? "" : " AND p.vote_points >" + votePoints;
    likePointsCondition =
      likePoints == null ? "" : " AND p.like_points >" + likePoints;
    laughPointsCondition =
      laughPoints == null ? "" : " AND p.laugh_points >" + laughPoints;

    /* 
      用户未登录，获取帖子时，不获取互动状态
      User not loged in, therefore fetch posts without interactions
     */
    if (meId == null) {
      Query queryRes = em
        .createNativeQuery(
          "SELECT u.id, u.username, p.id AS postId," +
          " p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt," +
          " p.title, p.content, p.view_count, p.vote_points, p.like_points," +
          " p.confused_points, p.laugh_points, p.comment_amounts" +
          " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
          " WHERE p.created_at < :cursor" + // cursor
          dateBeforeCondition +
          votePointsCondition +
          laughPointsCondition +
          likePointsCondition +
          " ORDER BY p.created_at DESC LIMIT :fetchCountPlusOne OFFSET :offset", // fetchCountPlusOne & offset
          "HomePostWithoutInteractions" // SQL to POJO
        )
        .setParameter("offset", offset)
        .setParameter("cursor", timeFrame)
        .setParameter("fetchCountPlusOne", fetchAmountPlusOne);

      List<PostInfoWithoutInteractions> postList = (List<PostInfoWithoutInteractions>) queryRes.getResultList();

      return buildPaginatedPostsRO(postList, fetchAmountPlusOne);
    }

    /* 
      用户已登录，获取帖子时，获取互动状态
      User loged in, therefore fetch posts with interactions
     */
    Query queryRes = em
      .createNativeQuery(
        "SELECT u.id, u.username, p.id AS postId, p.created_at AS postCreatedAt," +
        " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count," +
        " p.vote_points, p.like_points, p.confused_points, p.laugh_points," +
        " p.comment_amounts, i.vote_status, i.like_status, i.laugh_status, i.confused_status" +
        " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
        " LEFT JOIN interactions i ON i.post_id = p.id AND i.user_id = :meId" + // meId
        " WHERE p.created_at < :cursor " + // cursor
        dateBeforeCondition +
        votePointsCondition +
        laughPointsCondition +
        likePointsCondition +
        " ORDER BY p.created_at DESC" +
        " LIMIT :fetchCountPlusOne OFFSET :offset", // fetchCountPlusOne & offset
        "HomePostWithInteractions" // SQL to POJO
      )
      .setParameter("meId", meId)
      .setParameter("offset", offset)
      .setParameter("cursor", timeFrame)
      .setParameter("fetchCountPlusOne", fetchAmountPlusOne);

    List<PostInfoWithInteractions> postList = (List<PostInfoWithInteractions>) queryRes.getResultList();

    return buildPaginatedPostsROWithInteractions(postList, fetchAmountPlusOne);
  }

  public PostAndInteractions fetchSinglePost(Long postId, Long meId) {
    /* 
      用户未登录，获取帖子时，不获取互动状态
      User not loged in, therefore fetch post without interactions
     */
    if (meId == null) {
      PostInfoWithoutInteractions postList = postMapper.getPostInfoWithoutInteractions(
        postId
      );

      // 将 POJO 转换成响应对象（Map POJO to response object）
      HomePostMapper mapper = Mappers.getMapper(HomePostMapper.class);
      return mapper.toPostAndInteractions(postList);
    }

    /* 
      用户已登录，获取帖子时，获取互动状态
      User loged in, therefore fetch post with interactions
     */
    PostInfoWithInteractions postList = postMapper.getPostInfoWithInteractions(
      postId,
      meId
    );

    // 将 POJO 转换成响应对象（Map POJO to response object）
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
      /* 
        截取帖子内容到50个字符 
        Slice post content and only send 50 char
       */
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
      /* 
        截取帖子内容到50个字符 
        Slice post content and only send 50 char
       */
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

  private String daysBefore(Integer days) {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    .format(Date.from(Instant.now().minus(Duration.ofDays(days))));
  }

  @Transactional
  public void test() {
    Post post = postRepo.findById(1L).orElse(null);
    System.out.println(post.getInteractions().toString());

    throw new IllegalStateException("uncaught expection...");
    // PostInEdit postInEdit = postRepo.getPostInEditByid(1L).orElse(null);
    // System.out.println(postInEdit.getTitle());
  }
}
