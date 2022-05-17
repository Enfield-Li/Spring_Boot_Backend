package com.example.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class RedditApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext configContext = SpringApplication.run(
      RedditApplication.class,
      args
    );
    // UserService userService = configContext.getBean(UserService.class);

    // userService.newUser("user1", "user1", "user1@gmail.com");
    // userService.newUser("user2", "user2", "user2@gmail.com");
    // userService.newUser("user3", "user3", "user3@gmail.com");
    // userService.newUser("user4", "user4", "user4@gmail.com");
    // userService.newUser("user5", "user5", "user5@gmail.com");
    // userService.newUser("user6", "user6", "user6@gmail.com");

  }
}
