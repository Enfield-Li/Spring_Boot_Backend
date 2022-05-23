package com.example.reddit;

import com.example.reddit.post.repository.PostMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class RedditApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(
      RedditApplication.class,
      args
    );

    PostMapper postMapper = ctx.getBean(PostMapper.class);
  }
}
