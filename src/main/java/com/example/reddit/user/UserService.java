package com.example.reddit.user;

import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.ResUserError;
import com.example.reddit.user.dto.response.UserProfile;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.entity.Password;
import com.example.reddit.user.entity.User;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
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
        user = userRepository.findByEmail(usernameOrEmail).orElseThrow();
      } else {
        user = userRepository.findByUsername(usernameOrEmail).orElseThrow();
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
      User user = userRepository.findById(id).orElseThrow();

      return this.buildResUser(user, id);
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  public ResUser fetchUserInfo(Long id, Long meId) {
    return null;
  }

  @SuppressWarnings("unchecked")
  public List<UserProfile> fetchUserProfile(Long userId, Long meId) {
    if (meId == null) {
      Query queryResWithoutInteraction = em
        .createNativeQuery(
          "SELECT u.id, u.created_at AS userCreatedAt," +
          " u.username, u.email, u.post_amounts," +
          " p.id AS postId, p.created_at AS postCreatedAt," +
          " p.updated_at AS postUpdatedAt, p.title," +
          " p.content, p.view_count, p.vote_points," +
          " p.like_points, p.confused_points," +
          " p.laugh_points, p.comment_amounts" +
          " FROM user u JOIN post p" +
          " ON p.user_id = u.id" +
          " WHERE u.id = :userId",
          "userProfileWithoutInteractions"
        )
        .setParameter("userId", userId);

      return (List<UserProfile>) queryResWithoutInteraction.getResultList();
    } else {
      Query queryResWithInteraction = em
        .createNativeQuery(
          "SELECT u.id, u.created_at AS userCreatedAt," +
          " u.username, u.email, u.post_amounts," +
          " p.id AS postId, p.created_at AS postCreatedAt," +
          " p.updated_at AS postUpdatedAt, p.title," +
          " p.content, p.view_count, p.vote_points," +
          " p.like_points, p.confused_points, p.laugh_points," +
          " p.comment_amounts, i.created_at AS interactionCreatedAt," +
          " i.updated_at AS interactionUpdatedAt," +
          " i.vote_status, i.like_status, i.laugh_status," +
          " i.confused_status, i.have_read, i.have_checked" +
          " FROM user u JOIN post p ON p.user_id = u.id" +
          " JOIN interactions i ON i.user_id = u.id AND i.post_id = p.id" +
          " WHERE u.id = :userId",
          "userProfileWithInteractions"
        )
        .setParameter("userId", userId);

      return null;
    }
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
}
