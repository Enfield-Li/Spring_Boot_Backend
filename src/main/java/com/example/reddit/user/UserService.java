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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    String password = loginUserDto.getPassword();

    User user;
    if (usernameOrEmail.contains("@")) {
      user = userRepository.findByEmail(usernameOrEmail);
    } else {
      user = userRepository.findByUsername(usernameOrEmail);
    }

    // System.out.println(user.getPassword().toString());
    // System.out.println(
    //   user.getPassword().matchPassword("123456", this.passwordEncoder)
    // );

    ResUserError resUserError = new ResUserError();
    if (password != user.getPassword().toString()) {
      resUserError.setField("password");
      resUserError.setMessage("Invalid password");
      return new UserRO(resUserError);
    }

    ResUser resUser = new ResUser(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getCreatedAt(),
      user.getPostAmounts()
    );

    return new UserRO(resUser);
  }

  public UserRO createUser(CreateUserDto createUserDto, HttpSession session) {
    try {
      User newUser = new User(
        createUserDto.getUsername(),
        createUserDto.getEmail(),
        createUserDto.getPassword()
      );

      User user = userRepository.save(newUser);
      session.setAttribute("userId", user.getId());
    } catch (Exception e) {
      //TODO: handle exception
    }

    return new UserRO();
  }

  public UserRO me(Long id) {
    User user = userRepository
      .findById(id)
      .orElseThrow(() -> new IllegalStateException("User does not exist!"));

    ResUser resUser = new ResUser(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getCreatedAt(),
      user.getPostAmounts()
    );

    return new UserRO(resUser);
  }

  public ResUser fetchUserInfo(Long id, Long meId) {
    return new ResUser();
  }
}
