package com.example.reddit;

import com.example.reddit.employee.Employees;
import com.example.reddit.employee.dto.EmployeeName;
import com.example.reddit.employee.repository.EmployeesRepository;
import com.example.reddit.employee.repository.MyBatisMapper;
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

    // MyBatisMapper mapper = configContext.getBean(MyBatisMapper.class);

    // Employees mapperRes = mapper.mybatisfindById(1L);
    // System.out.println(mapperRes);

    // Employees jpaRes = employeeRepository.findById(1L).orElse(null);
    // if (jpaRes != null) System.out.println(jpaRes.toString());

    EmployeeName jpaRes = employeeRepository.mybatisFindById(1L);

    User user = userRepository.findUserByIdWM("user1");
    User user2 = userDao.findUserByIdWM("user2");

    UserInfo userInfo = userMapper.findUserInfoByIdWM(2L);

    // System.out.println(jpaRes.toString());
    System.out.println("user: " + user.toString());
    System.out.println("user from dao: " + user2.toString());
    // System.out.println("userInfo: " + userInfo.toString());

  }
}
