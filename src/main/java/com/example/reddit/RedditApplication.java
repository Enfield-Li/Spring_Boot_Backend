package com.example.reddit;

import com.example.reddit.employee.Employees;
import com.example.reddit.employee.dto.EmployeeName;
import com.example.reddit.employee.repository.EmployeesRepository;
import com.example.reddit.employee.repository.MyBatisMapper;
import com.example.reddit.post.dto.PostWithUserInfo;
import com.example.reddit.post.repository.PostMapper;
import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;
import com.example.reddit.user.repository.UserDao;
import com.example.reddit.user.repository.UserMapper;
import com.example.reddit.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedditApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext configContext = SpringApplication.run(
      RedditApplication.class,
      args
    );
    EmployeesRepository employeeRepository = configContext.getBean(
      EmployeesRepository.class
    );

    UserRepository userRepository = configContext.getBean(UserRepository.class);
    UserMapper userMapper = configContext.getBean(UserMapper.class);
    UserDao userDao = configContext.getBean(UserDao.class);

    PostMapper postMapper = configContext.getBean(PostMapper.class);
    
    PostWithUserInfo postRes1 = postMapper.getPostWithUserInfoById(1L);
    System.out.println(postRes1.toString());
  }
}
