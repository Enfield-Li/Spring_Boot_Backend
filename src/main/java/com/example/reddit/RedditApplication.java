package com.example.reddit;

import com.example.reddit.mapper.demo.SimpleDestination;
import com.example.reddit.mapper.demo.SimpleSource;
import com.example.reddit.mapper.demo.SimpleSourceDestinationMapper;
import com.example.reddit.mapper.nested.Employee;
import com.example.reddit.mapper.nested.EmployeeDTO;
import com.example.reddit.mapper.nested.EmployeeMapper;
import com.example.reddit.user.UserService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
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

    // EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    // Employee entity = new Employee();
    // entity.setId(1);
    // entity.setName("user1");
    // entity.setDivisionId(2);
    // entity.setDivisionName("divisionName");

    // EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);

    // System.out.println(dto.getDivision().toString());
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
