package com.example.reddit.user;

import com.example.reddit.mapper.UserPostMapper;
import com.example.reddit.mapper.dto.homePost.PostAndInteractions;
import com.example.reddit.mapper.dto.userPost.UserPaginatedPosts;
import com.example.reddit.mapper.dto.userPost.UserPostAndInteractions;
import com.example.reddit.mapper.source.PostInfoWithInteractions;
import com.example.reddit.mapper.source.PostInfoWitoutInteractions;
import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.ResUserError;
import com.example.reddit.user.dto.response.UserInfo;
import com.example.reddit.user.dto.response.UserProfileRO;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.entity.Password;
import com.example.reddit.user.entity.User;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
  private Integer takeAmount = 10;

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

  public void newUser(String username, String password, String email) {
    Password pass = Password.encode(password, this.passwordEncoder);
    User user = User.of(username, email, pass);
    userRepository.save(user);
  }

  public UserRO login(LoginUserDto dto, HttpSession session) {
    try {
      User user;
      // Parse credentials
      String usernameOrEmail = dto.getUsernameOrEmail();

      // Check if user exist
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

      // Check password
      if (
        !user
          .getPassword()
          .matchPassword(dto.getPassword(), this.passwordEncoder)
      ) {
        return new UserRO(ResUserError.of("password"));
      }

      // Keep user login session state
      session.setAttribute("userId", user.getId());

      return new UserRO(this.buildResUser(user, null));
    } catch (NoSuchElementException e) {
      // Catch user does not exist
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

      return new UserRO(this.buildResUser(user, user.getId()));
    } catch (DataIntegrityViolationException e) {
      return new UserRO(ResUserError.of("usernameOrEmail"));
    }
  }

  public ResUser me(Long id) {
    try {
      User user = userRepository
        .findById(id)
        .orElseThrow(NoSuchElementException::new);

      return this.buildResUser(user, id);
    } catch (NoSuchElementException e) {
      return null;
    }
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
    takeAmount = take == null ? takeAmount : take;

    Integer fetchCount = Math.min(takeAmount, 25);
    Integer fetchCountPlusOne = fetchCount + 1;
    Integer offset = cursor == null ? 0 : 1;
    Instant timeFrame = cursor == null ? Instant.now() : cursor;

    if (meId == null) {
      Query queryResWithoutInteraction = em
        .createNativeQuery(
          "SELECT u.id, u.created_at AS userCreatedAt," +
          " u.username, u.email, u.post_amounts, p.id AS postId," +
          " p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt," +
          " p.title, p.content, p.view_count, p.vote_points, p.like_points," +
          " p.confused_points, p.laugh_points, p.comment_amounts" +
          " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
          " WHERE p.user_id = :userId AND p.created_at < :cursor" +
          " ORDER BY p.created_at DESC LIMIT :fetchCountPlusOne OFFSET :offset",
          "UserProfileWithoutInteractions"
        )
        .setParameter("offset", offset)
        .setParameter("cursor", timeFrame)
        .setParameter("fetchCountPlusOne", fetchCountPlusOne)
        .setParameter("userId", userId);

      List<PostInfoWitoutInteractions> userProfileList = (List<PostInfoWitoutInteractions>) queryResWithoutInteraction.getResultList();
      Boolean hasMore = userProfileList.size() == fetchCountPlusOne;

      userProfileList.remove(userProfileList.size() - 1);

      return this.buildUserProfileRO(userProfileList, userId, hasMore, meId);
    }

    Query queryResWithInteraction = em
      .createNativeQuery(
        "SELECT u.id, u.created_at AS userCreatedAt, u.username, u.email," +
        " u.post_amounts, p.id AS postId, p.created_at AS postCreatedAt," +
        " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count," +
        " p.vote_points, p.like_points, p.confused_points, p.laugh_points," +
        " p.comment_amounts, i.created_at AS interactionCreatedAt," +
        " i.updated_at AS interactionUpdatedAt, i.vote_status, i.like_status," +
        " i.laugh_status, i.confused_status, i.have_read, i.have_checked" +
        " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
        " LEFT JOIN interactions i ON i.post_id = p.id" +
        " AND i.user_id = :meId WHERE p.user_id = :userId AND p.created_at < :cursor" +
        " ORDER BY p.created_at DESC LIMIT :fetchCountPlusOne OFFSET :offset",
        "UserProfileWithInteractions"
      )
      .setParameter("meId", meId)
      .setParameter("userId", userId)
      .setParameter("offset", offset)
      .setParameter("cursor", timeFrame)
      .setParameter("fetchCountPlusOne", fetchCountPlusOne);

    List<PostInfoWithInteractions> userProfileList = (List<PostInfoWithInteractions>) queryResWithInteraction.getResultList();
    Boolean hasMore = userProfileList.size() == fetchCountPlusOne;

    userProfileList.remove(userProfileList.size() - 1);

    return buildUserProfileRO(userProfileList, userId, hasMore, meId);
  }

  private ResUser buildResUser(User user, Long meId) {
    return ResUser.of(
      user.getId(),
      user.getUsername(),
      meId == user.getId() ? user.getEmail() : null,
      user.getCreatedAt(),
      user.getPostAmounts()
    );
  }

  private <T extends PostInfoWitoutInteractions> UserProfileRO buildUserProfileRO(
    List<T> userProfileList,
    Long userId,
    Boolean hasMore,
    Long meId
  ) {
    UserPostMapper mapper = Mappers.getMapper(UserPostMapper.class);
    List<UserPostAndInteractions> postAndInteractionsList = new ArrayList<>();

    for (T sourceItem : userProfileList) {
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

  private <T extends PostInfoWitoutInteractions> UserInfo buildUserInfo(
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
