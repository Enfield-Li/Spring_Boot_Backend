package com.example.reddit.user;

import com.example.reddit.user.dto.CreateUserDto;
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
  UserRepository userRepository;

  @GetMapping
  public List<User> getAll() {
    try {
      List<User> users = userRepository.findAll();

      return users;
    } catch (Exception e) {
      throw new IllegalStateException("Internal Error");
    }
  }

  @GetMapping("{id}")
  public Object getById(
    @PathVariable("id") Long id,
    HttpSession session
  ) {
    System.out.println(session.getAttribute("userId"));
    return session.getAttribute("userId");
  }

  @PostMapping
  public User create(
    @Valid @RequestBody CreateUserDto createUserDto,
    HttpSession session
  ) {
    User user = userRepository.findByUsername(createUserDto.getUsername());

    session.setAttribute("userId", user.getId());
    System.out.println(session.getAttribute("userId"));

    return user;
  }

  @PutMapping("{id}")
  public void update(@PathVariable("id") Long id, @RequestBody User item) {}

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") Long id) {}
}
