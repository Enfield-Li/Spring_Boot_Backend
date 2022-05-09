package com.example.reddit.user;

import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.ResUserError;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.entity.Password;
import com.example.reddit.user.entity.User;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Autowired
  UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void newUser(String username, String pass, String email) {
    Password password = Password.encode(pass, this.passwordEncoder);

    User newUser = User.of(username, email, password);

    userRepository.save(newUser);
  }

  public UserRO login(LoginUserDto dto, HttpSession session) {
    try {
      User user;
      String usernameOrEmail = dto.getUsernameOrEmail();

      if (usernameOrEmail.contains("@")) {
        user = userRepository.findByEmail(usernameOrEmail).orElseThrow();
      } else {
        user = userRepository.findByUsername(usernameOrEmail).orElseThrow();
      }

      if (
        !user
          .getPassword()
          .matchPassword(dto.getPassword(), this.passwordEncoder)
      ) {
        return new UserRO(this.buildErrorRO("password"));
      }

      session.setAttribute("userId", user.getId());

      return new UserRO(this.buildResUser(user, null));
    } catch (Exception e) {
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
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Invalid username or email"
      );
    }
  }

  public ResUser me(Long id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(
        () ->
          new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
      );

    return this.buildResUser(user, id);
  }

  public ResUser fetchUserInfo(Long id, Long meId) {
    return null;
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
