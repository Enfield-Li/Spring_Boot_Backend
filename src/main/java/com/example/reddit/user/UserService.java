package com.example.reddit.user;

import com.example.reddit.mapper.UserPostMapper;
import com.example.reddit.mapper.source.userPost.UserPostInfoWithInteractions;
import com.example.reddit.mapper.source.userPost.UserPostInfoWithoutInteractions;
import com.example.reddit.mapper.target.userPost.UserPaginatedPosts;
import com.example.reddit.mapper.target.userPost.UserPostAndInteractions;
import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.ResUserError;
import com.example.reddit.user.dto.response.UserInfo;
import com.example.reddit.user.dto.response.UserProfileRO;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.entity.Password;
import com.example.reddit.user.entity.User;
import com.example.reddit.user.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final EntityManager em;

  @Autowired
  UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    EntityManager em
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.em = em;
  }

  public User getUserInfo(Long id, Long meId) {
    User user = userRepository
      .findById(id)
      .orElseThrow(NoSuchElementException::new);

    /* 
      用户邮箱只对自己可见
      User can only see their own email
     */
    if (user.getId() != meId) user.setEmail(null);

    return user;
  }

  public void newUser(String username, String password, String email) {
    Password pass = Password.encode(password, this.passwordEncoder);
    User user = User.of(username, email, pass);
    userRepository.save(user);
  }

  public UserRO login(LoginUserDto dto, HttpSession session) {
    try {
      User user;

      String usernameOrEmail = dto.getUsernameOrEmail();

      /* 
        检查用户是否存在 
        Check if user exist
       */
      if (usernameOrEmail.contains("@")) {
        user =
          userRepository
            .findByEmail(usernameOrEmail)
            .orElseThrow(NoSuchElementException::new);
      } else {
        user =
          userRepository
            .findByUsername(usernameOrEmail)
            .orElseThrow(NoSuchElementException::new);
      }

      /* 
        核对密码 
        Check password
       */
      if (
        !user
          .getPassword()
          .matchPassword(dto.getPassword(), this.passwordEncoder)
      ) {
        return new UserRO(ResUserError.of("password"));
      }

      /* 
        保存用户登录状态
        Keep user login session state
       */
      session.setAttribute("userId", user.getId());

      return new UserRO(this.buildResUser(user));
    } catch (NoSuchElementException e) {
      /* 
        返还错误信息
        Catch user does not exist
       */
      return new UserRO(ResUserError.of("usernameOrEmail"));
    }
  }

  public UserRO createUser(CreateUserDto dto, HttpSession session) {
    try {
      Password password = Password.encode(
        dto.getPassword(),
        this.passwordEncoder
      );

      User newUser = User.of(dto.getUsername(), dto.getEmail(), password);

      User user = userRepository.save(newUser);

      session.setAttribute("userId", user.getId());

      return new UserRO(this.buildResUser(user));
    } catch (DataIntegrityViolationException e) {
      return new UserRO(ResUserError.of("usernameOrEmail"));
    }
  }

  public ResUser me(Long id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(NoSuchElementException::new);

    return this.buildResUser(user);
  }

  public ResUser fetchUserInfo(Long id, Long meId) {
    return null;
  }

  public void fetchOneUserProfile() {}

  @SuppressWarnings("unchecked")
  public UserProfileRO fetchUserProfile(
    Long userId,
    Long meId,
    Instant cursor,
    Integer take
  ) {
    /* 
      设置需要插入/更新的初始值 
      Set up initial values to be inserted/updated
     */
    Integer takeAmount = take == null ? 10 : take; // Default amount: 10
    Integer fetchCount = Math.min(takeAmount, 25);
    Integer fetchCountPlusOne = fetchCount + 1;

    Integer offset = cursor == null ? 0 : 1;
    Instant timeFrame = cursor == null ? Instant.now() : cursor;

    /*
      用户先前无互动状态，创建新的互动
      User has no previous interactions, therefore create
     */
    if (meId == null) {
      Query queryRes = em
        .createNativeQuery(
          "SELECT u.id, u.created_at AS userCreatedAt," +
          " u.username, u.email, u.post_amounts, p.id AS postId," +
          " p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt," +
          " p.title, p.content, p.view_count, p.vote_points, p.like_points," +
          " p.confused_points, p.laugh_points, p.comment_amounts" +
          " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
          " WHERE p.user_id = :userId AND p.created_at < :cursor" + // userId & cursor
          " ORDER BY p.created_at DESC LIMIT :fetchCountPlusOne OFFSET :offset", // fetchCountPlusOne & offset
          "UserProfileWithoutInteractions" // SQL to POJO
        )
        .setParameter("offset", offset)
        .setParameter("cursor", timeFrame)
        .setParameter("fetchCountPlusOne", fetchCountPlusOne)
        .setParameter("userId", userId);

      List<UserPostInfoWithoutInteractions> userProfileList = (List<UserPostInfoWithoutInteractions>) queryRes.getResultList();

      return this.buildUserProfileRO(
          userProfileList,
          userId,
          meId,
          fetchCountPlusOne
        );
    }

    /*
      用户先前有互动，更新互动状态
      User has previous interactions, therefore update
     */
    Query queryRes = em
      .createNativeQuery(
        "SELECT u.id, u.created_at AS userCreatedAt, u.username, u.email," +
        " u.post_amounts, p.id AS postId, p.created_at AS postCreatedAt," +
        " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count, p.vote_points," +
        " p.like_points, p.confused_points, p.laugh_points, p.comment_amounts," +
        " i.vote_status, i.like_status, i.laugh_status, i.confused_status" +
        " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
        " LEFT JOIN interactions i ON i.post_id = p.id AND i.user_id = :meId" + // meId
        " WHERE p.user_id = :userId AND p.created_at < :cursor" + // userId & cursor
        " ORDER BY p.created_at DESC LIMIT :fetchCountPlusOne OFFSET :offset", // fetchCountPlusOne & offset
        "UserProfileWithInteractions" // SQL to POJO
      )
      .setParameter("meId", meId)
      .setParameter("userId", userId)
      .setParameter("offset", offset)
      .setParameter("cursor", timeFrame)
      .setParameter("fetchCountPlusOne", fetchCountPlusOne);

    List<UserPostInfoWithInteractions> userProfileList = (List<UserPostInfoWithInteractions>) queryRes.getResultList();

    return buildUserProfileROWithInteractions(
      userProfileList,
      userId,
      meId,
      fetchCountPlusOne
    );
  }

  private ResUser buildResUser(User user) {
    return ResUser.of(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getCreatedAt(),
      user.getPostAmounts()
    );
  }

  private UserProfileRO buildUserProfileRO(
    List<UserPostInfoWithoutInteractions> userProfileList,
    Long userId,
    Long meId,
    Integer fetchCountPlusOne
  ) {
    Boolean hasMore = userProfileList.size() == fetchCountPlusOne;

    if (hasMore) userProfileList.remove(userProfileList.size() - 1);

    UserPostMapper mapper = Mappers.getMapper(UserPostMapper.class);
    List<UserPostAndInteractions> postAndInteractionsList = new ArrayList<>();

    for (UserPostInfoWithoutInteractions sourceItem : userProfileList) {
      /* 
        截取帖子内容到50个字符 
        Slice post content and only send 50 char
       */
      String postContent = sourceItem.getContent();
      if (postContent != null && postContent.length() > 50) {
        String contentSnippet = postContent.substring(0, 50);
        sourceItem.setContent(contentSnippet);
      }

      UserPostAndInteractions dtoItem = mapper.toPostAndInteractions(
        sourceItem
      );
      postAndInteractionsList.add(dtoItem);
    }

    UserPaginatedPosts userPaginatedPost = new UserPaginatedPosts(
      hasMore,
      postAndInteractionsList
    );

    UserInfo userInfo = buildUserInfo(userProfileList, meId);

    return new UserProfileRO(userInfo, userPaginatedPost);
  }

  private UserProfileRO buildUserProfileROWithInteractions(
    List<UserPostInfoWithInteractions> userProfileList,
    Long userId,
    Long meId,
    Integer fetchCountPlusOne
  ) {
    Boolean hasMore = userProfileList.size() == fetchCountPlusOne;

    if (hasMore) userProfileList.remove(userProfileList.size() - 1);

    UserPostMapper mapper = Mappers.getMapper(UserPostMapper.class);
    List<UserPostAndInteractions> postAndInteractionsList = new ArrayList<>();

    for (UserPostInfoWithInteractions sourceItem : userProfileList) {
      /* 
        截取帖子内容到50个字符 
        Slice post content and only send 50 char
       */
      String postContent = sourceItem.getContent();
      if (postContent != null && postContent.length() > 50) {
        String contentSnippet = postContent.substring(0, 50);
        sourceItem.setContent(contentSnippet);
      }

      UserPostAndInteractions dtoItem = mapper.toPostAndInteractions(
        sourceItem
      );
      postAndInteractionsList.add(dtoItem);
    }

    UserPaginatedPosts userPaginatedPost = new UserPaginatedPosts(
      hasMore,
      postAndInteractionsList
    );

    UserInfo userInfo = buildUserInfo(userProfileList, meId);

    return new UserProfileRO(userInfo, userPaginatedPost);
  }

  private <T extends UserPostInfoWithoutInteractions> UserInfo buildUserInfo(
    List<T> userProfileList,
    Long meId
  ) {
    T userProfile = userProfileList.get(0);

    return new UserInfo(
      userProfile.getId(),
      userProfile.getUserCreatedAt(),
      meId == userProfile.getId() ? userProfile.getEmail() : null,
      userProfile.getPostAmounts(),
      userProfile.getUsername()
    );
  }
}
