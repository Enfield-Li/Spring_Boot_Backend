package com.example.reddit.user;

import com.example.reddit.user.dto.interfaces.UserInfo;
import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.UserProfile;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user")
@RestController
@RequestMapping("user")
class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  @Autowired
  UserController(UserService userService, UserRepository userRepository) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @GetMapping("test1")
  public List<User> test1() {
    return userRepository.findAll();
  }

  @GetMapping("test2/{id}")
  public UserInfo test2(@PathVariable("id") Long id) {
    return userRepository.findByid(id).orElseThrow();
  }

  @PostMapping("register")
  public ResponseEntity register(
    @Valid @RequestBody CreateUserDto createUserDto,
    HttpSession session
  ) {
    try {
      UserRO userRo = userService.createUser(createUserDto, session);
      return ResponseEntity.status(HttpStatus.CREATED).body(userRo);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @GetMapping("profile/{id}")
  public List<UserProfile> findOne(
    @PathVariable("id") Long id,
    HttpSession session
  ) {
    Long meId = (Long) session.getAttribute("userId");

    return userService.fetchUserProfile(id, meId);
  }

  @GetMapping("userInfo/{id}")
  public void findUserProfile(
    @PathVariable("id") Long id,
    HttpSession session
  ) {}

  @PutMapping("login")
  public ResponseEntity login(LoginUserDto loginUserDto, HttpSession session) {
    try {
      UserRO userRo = userService.login(loginUserDto, session);
      return ResponseEntity.status(HttpStatus.CREATED).body(userRo);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @GetMapping("me")
  public ResponseEntity loginUser(HttpSession session) {
    try {
      Long userId = (Long) session.getAttribute("userId");
      if (userId == null) return null;

      ResUser resUser = userService.me(userId);
      return ResponseEntity.status(HttpStatus.CREATED).body(resUser);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Something's gone wrong...");
    }
  }

  @PatchMapping("update-user/{id}")
  public void updateUser(@PathVariable("id") Long id, HttpSession session) {}

  @GetMapping("logout")
  public void logoutUser() {}

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") Long id) {}
}
