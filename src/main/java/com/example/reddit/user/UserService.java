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

  public UserRO login(LoginUserDto loginUserDto, HttpSession session) {
    String usernameOrEmail = loginUserDto.getUsernameOrEmail();

    User user;
    if (usernameOrEmail.contains("@")) {
      user =
        userRepository
          .findByEmail(usernameOrEmail)
          .orElseThrow(
            () ->
              new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid email"
              )
          );
    } else {
      user =
        userRepository
          .findByUsername(usernameOrEmail)
          .orElseThrow(
            () ->
              new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid username"
              )
          );
    }

    if (
      !user
        .getPassword()
        .matchPassword(loginUserDto.getPassword(), this.passwordEncoder)
    ) {
      return new UserRO(this.buildErrorRO("password"));
    }

    session.setAttribute("userId", user.getId());

    return new UserRO(this.buildResUser(user, null));
  }

  public UserRO createUser(CreateUserDto createUserDto, HttpSession session) {
    try {
      Password password = Password.encode(
        createUserDto.getPassword(),
        this.passwordEncoder
      );

      User newUser = new User(
        createUserDto.getUsername(),
        createUserDto.getEmail(),
        password
      );

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
