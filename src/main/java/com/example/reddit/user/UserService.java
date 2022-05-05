package com.example.reddit.user;

import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.entity.User;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  UserRepository userRepository;

  @Autowired
  UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User login(LoginUserDto loginUserDto, HttpSession session) {
    return new User();
  }

  public UserRO createUser(CreateUserDto createUserDto, HttpSession session) {
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
