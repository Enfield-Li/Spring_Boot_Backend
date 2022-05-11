package com.example.reddit.user;

import com.example.reddit.user.dto.db.UserAtZero;
import com.example.reddit.user.dto.db.UserMapper;
import com.example.reddit.user.dto.db.UserProfileWithInteractions;
import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.ResUserError;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.dto.response.userProfile.Interactions;
import com.example.reddit.user.dto.response.userProfile.Post;
import com.example.reddit.user.dto.response.userProfile.PostAndInteractions;
import com.example.reddit.user.dto.response.userProfile.UserInfo;
import com.example.reddit.user.dto.response.userProfile.UserPaginatedPost;
import com.example.reddit.user.dto.response.userProfile.UserProfile;
import com.example.reddit.user.dto.response.userProfile.UserProfileRO;
import com.example.reddit.user.entity.Password;
import com.example.reddit.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final EntityManager em;
  private final String queryStrWithoutInteraction =
    "SELECT u.id, u.created_at AS userCreatedAt," +
    " u.username, u.email, u.post_amounts, p.id AS postId," +
    " p.created_at AS postCreatedAt, p.updated_at AS postUpdatedAt," +
    " p.title, p.content, p.view_count, p.vote_points, p.like_points," +
    " p.confused_points, p.laugh_points, p.comment_amounts" +
    " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
    " WHERE p.user_id = :userId";
  private final String queryStrWithInteraction =
    "SELECT u.id, u.created_at AS userCreatedAt, u.username, u.email," +
    " u.post_amounts, p.id AS postId, p.created_at AS postCreatedAt," +
    " p.updated_at AS postUpdatedAt, p.title, p.content, p.view_count," +
    " p.vote_points, p.like_points, p.confused_points, p.laugh_points," +
    " p.comment_amounts, i.created_at AS interactionCreatedAt," +
    " i.updated_at AS interactionUpdatedAt, i.vote_status, i.like_status," +
    " i.laugh_status, i.confused_status, i.have_read, i.have_checked" +
    " FROM post p LEFT JOIN user u ON p.user_id = u.id" +
    " LEFT JOIN interactions i ON i.post_id = p.id" +
    " AND i.user_id = :meId WHERE p.user_id = :userId";

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
        return new UserRO(this.buildErrorRO("password"));
      }

      // Keep user login session state
      session.setAttribute("userId", user.getId());

      return new UserRO(this.buildResUser(user, null));
    } catch (NoSuchElementException e) {
      // Catch user does not exist
      return new UserRO(this.buildErrorRO("usernameOrEmail"));
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
      return new UserRO(this.buildErrorRO("usernameOrEmail"));
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

  public UserAtZero fetchOneUserProfile() {
    return null;
  }

  @SuppressWarnings("unchecked")
  public UserProfileRO fetchUserProfile(Long userId, Long meId) {
    if (meId == null) {
      Query queryResWithoutInteraction = em
        .createNativeQuery(
          queryStrWithoutInteraction,
          "userProfileWithoutInteractions"
        )
        .setParameter("userId", userId);

      List<UserProfile> userProfileList = (List<UserProfile>) queryResWithoutInteraction.getResultList();

      return this.buildUserProfileRO(userProfileList, userId);
    }

    Query queryResWithInteraction = em
      .createNativeQuery(queryStrWithInteraction, "userProfileWithInteractions")
      .setParameter("meId", meId)
      .setParameter("userId", userId);

    List<UserProfileWithInteractions> userProfileList = (List<UserProfileWithInteractions>) queryResWithInteraction.getResultList();

    return buildUserProfileRO(userProfileList, userId);
  }

  private ResUser buildResUser(User user, Long meId) {
    return ResUser.of(
      user.getId(),
      user.getUsername(),
      meId == null ? null : user.getEmail(),
      user.getCreatedAt(),
      user.getPostAmounts()
    );
  }

  private ResUserError buildErrorRO(String field) {
    return ResUserError.of(field);
  }

  private <T extends UserProfile> UserProfileRO buildUserProfileRO(
    List<T> userProfileList,
    Long userId
  ) {
    UserInfo userInfo = buildUserInfo(userProfileList);
    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    List<PostAndInteractions> postAndInteractionsList = new ArrayList<>();

    for (T users : userProfileList) {
      PostAndInteractions postAndInteractions = mapper.userProfileToPostAndInteractions(
        users
      );
      postAndInteractionsList.add(postAndInteractions);
    }

    UserPaginatedPost userPaginatedPost = new UserPaginatedPost(
      true,
      postAndInteractionsList
    );

    return new UserProfileRO(userInfo, userPaginatedPost);
  }

  private <T extends UserProfile> UserInfo buildUserInfo(
    List<T> userProfileList
  ) {
    T userProfile = userProfileList.get(0);

    return new UserInfo(
      userProfile.getId(),
      userProfile.getUserCreatedAt(),
      userProfile.getEmail(),
      userProfile.getPostAmounts(),
      userProfile.getUsername()
    );
  }
}
