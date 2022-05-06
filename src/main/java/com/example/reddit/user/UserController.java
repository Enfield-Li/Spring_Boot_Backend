package com.example.reddit.user;

import com.example.reddit.user.dto.request.CreateUserDto;
import com.example.reddit.user.dto.request.LoginUserDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user")
@RestController
@RequestMapping("/user")
class UserController {

  @Autowired
  UserService userService;

  // @GetMapping
  // public List<User> getAll() {
  //   try {
  //     List<User> users = userService.findAll();

  //     return users;
  //   } catch (Exception e) {
  //     throw new IllegalStateException("Internal Error");
  //   }
  // }

  @GetMapping("{id}")
  public void findOne(@PathVariable("id") Long id, HttpSession session) {}

  @PostMapping("/register")
  public UserRO register(
    @Valid @RequestBody CreateUserDto createUserDto,
    HttpSession session
  ) {
    return userService.createUser(createUserDto, session);
  }

  @PutMapping("/login")
  public UserRO login(LoginUserDto loginUserDto, HttpSession session) {
    return userService.login(loginUserDto, session);
  }

  @GetMapping("/me")
  public UserRO loginUser(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");

    if (userId == null) return null;

    return userService.me(userId);
  }

  @PutMapping("{id}")
  public void update(@PathVariable("id") Long id, @RequestBody User item) {}

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") Long id) {}
}