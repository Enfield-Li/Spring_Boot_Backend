package com.example.reddit;

import com.example.reddit.post.PostRepository;
import com.example.reddit.post.entity.Post;
import com.example.reddit.user.UserRepository;
import com.example.reddit.user.entity.Password;
import com.example.reddit.user.entity.User;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class RedditApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext configContext = SpringApplication.run(
      RedditApplication.class,
      args
    );
    // UserRepository userRepo = configContext.getBean(UserRepository.class);
    // PostRepository postRepo = configContext.getBean(PostRepository.class);
    // Password password1 = new Password("123");

    // User user1 = new User("user1", "user1@email.com", password1);
    // User user2 = new User("user2", "user2@email.com", password1);

    // Post post1 = new Post("pt1 belong to user1", "pc1", user1);
    // Post post2 = new Post("pt2 belong to user1", "pc2", user1);
    // Post post3 = new Post("pt3 belong to user2", "pc3", user2);

    // List<Post> user1Posts = Arrays.asList(post1, post2);
    // user1.setPost(user1Posts);

    // List<Post> user2Posts = Arrays.asList(post3);
    // user2.setPost(user2Posts);
    // userRepo.saveAll(Arrays.asList(user1, user2));
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
          .addMapping("/**")
          .allowedOrigins("http://localhost:3118")
          .allowedOrigins("http://localhost:3119")
          .allowCredentials(true);
      }
    };
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
