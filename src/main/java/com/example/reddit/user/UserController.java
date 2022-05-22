package com.example.reddit.user;

import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
import com.example.reddit.user.dto.response.UserProfileRO;
import com.example.reddit.user.dto.response.UserRO;
import com.example.reddit.user.entity.User;
import com.example.reddit.user.repository.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user")
@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:3118", maxAge = 3600)
class UserController {

  private final UserService userService;
  private final UserRepository userRepository;
  private static final Logger log = LoggerFactory.getLogger(
    UserController.class
  );

  @Autowired
  UserController(UserService userService, UserRepository userRepository) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @GetMapping("test")
  public User test1() {
    return userRepository.findById(1L).orElse(null);
  }

  @PostMapping("register")
  public ResponseEntity<?> register(
    @Valid @RequestBody CreateUserDto createUserDto,
    HttpSession session
  ) {
    UserRO userRo = userService.createUser(createUserDto, session);

    return ResponseEntity.status(HttpStatus.CREATED).body(userRo);
  }

  @GetMapping("profile/{id}")
  public ResponseEntity<?> getUserProfile(
    @PathVariable("id") Long id,
    HttpSession session,
    @RequestParam(
      name = "cursor",
      required = false
    ) @DateTimeFormat Instant cursor,
    @RequestParam(name = "take", required = false) Integer take
  ) {
    Long meId = (Long) session.getAttribute("userId");

    UserProfileRO res = userService.fetchUserProfile(id, meId, cursor, take);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
  }

  @GetMapping("userInfo/{id}")
  public ResponseEntity<?> getUserProfile(
    @PathVariable("id") Long id,
    HttpSession session
  ) {
    Long meId = (Long) session.getAttribute("userId");

    User user = userService.getUserInfo(id, meId);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
  }

  @PutMapping("login")
  public ResponseEntity<?> login(
    @Valid @RequestBody LoginUserDto loginUserDto,
    HttpSession session
  ) {
    UserRO userRo = userService.login(loginUserDto, session);
    return ResponseEntity.status(HttpStatus.CREATED).body(userRo);
  }

  @GetMapping("me")
  public ResponseEntity<?> loginUser(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) return null;

    ResUser resUser = userService.me(userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(resUser);
  }

  @PatchMapping("update-user/{id}")
  public ResponseEntity<?> updateUser(
    @PathVariable("id") Long id,
    HttpSession session
  ) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }

  @GetMapping("logout")
  public ResponseEntity<?> logoutUser(HttpSession session) {
    session.removeAttribute("userId");

    return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
  }
}
