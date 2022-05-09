package com.example.reddit.user;

import com.example.reddit.user.dto.interfaces.UserInfo;
import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
import com.example.reddit.user.dto.response.ResUser;
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
  public UserRO register(
    @Valid @RequestBody CreateUserDto createUserDto,
    HttpSession session
  ) {
    return userService.createUser(createUserDto, session);
  }

  @GetMapping("profile/{id}")
  public void findOne(@PathVariable("id") Long id, HttpSession session) {}

  @GetMapping("userInfo/{id}")
  public void findUserProfile(
    @PathVariable("id") Long id,
    HttpSession session
  ) {}

  @PutMapping("login")
  public UserRO login(LoginUserDto loginUserDto, HttpSession session) {
    
    return userService.login(loginUserDto, session);
  }

  @GetMapping("me")
  public ResUser loginUser(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");

    if (userId == null) return null;

    return userService.me(userId);
  }

  @PatchMapping("update-user/{id}")
  public void updateUser(@PathVariable("id") Long id, HttpSession session) {}

  @GetMapping("logout")
  public void logoutUser() {}

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") Long id) {}
}
