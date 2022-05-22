package com.example.reddit;

import java.util.List;

import com.example.reddit.employee.Employees;
import com.example.reddit.employee.dto.EmployeeName;
import com.example.reddit.employee.repository.EmployeesRepository;
import com.example.reddit.employee.repository.MyBatisMapper;
import com.example.reddit.post.Post;
import com.example.reddit.post.dto.PostWithUserInfo;
import com.example.reddit.post.repository.PostDao;
import com.example.reddit.post.repository.PostMapper;
import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;
import com.example.reddit.user.dto.UserProfile;
import com.example.reddit.user.dto.UserWithPost;
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

    List<UserWithPost> userProfile = userMapper.findUserProfile(1L);
    System.out.println(userProfile.size());

    PostMapper postMapper = configContext.getBean(PostMapper.class);
    PostDao postDao = configContext.getBean(PostDao.class);

    // List<Post> posts = postMapper.getAllPosts();
    // System.out.println(posts.toString());

    // PostWithUserInfo postRes2 = postDao.getPostWithUserInfoById(1L);
    // System.out.println("from dao: " + postRes2.toString());

    // PostWithUserInfo postRes1 = postMapper.getPostWithUserInfoById(1L);
    // System.out.println(postRes1.toString());
  }
}
