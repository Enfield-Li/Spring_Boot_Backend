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

  private final UserRepository userRepository;

  @Autowired
  UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("test1")
  public List<User> test1() {
    return userRepository.findAll();
  }

  @GetMapping("test2/{id}")
  public UserInfo test2(@PathVariable("id") Long id) {
    return userRepository.findByid(id).orElseThrow();
  }

  @GetMapping("test3")
  public List<User> test3() {
    return userRepository.findAll();
  }
}
