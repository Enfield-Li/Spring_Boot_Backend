package com.example.reddit;

import com.example.reddit.user.UserService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
    // UserService userService = configContext.getBean(UserService.class);

    // userService.newUser("user1", "user1", "user1@gmail.com");
    // userService.newUser("user2", "user2", "user2@gmail.com");
    // userService.newUser("user3", "user3", "user3@gmail.com");
    // userService.newUser("user4", "user4", "user4@gmail.com");
    // userService.newUser("user5", "user5", "user5@gmail.com");
    // userService.newUser("user6", "user6", "user6@gmail.com");

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

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .components(new Components())
      .info(new Info().title("Spring API").description("使用 Spring 搭建"));
  }
}
