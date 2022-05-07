package com.example.reddit;

import java.util.Arrays;
import java.util.List;
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
  }
}
