package com.example.reddit;

import com.example.reddit.employee.Employees;
import com.example.reddit.employee.EmployeesRepository;
import com.example.reddit.employee.MyBatisMapper;
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

    // MyBatisMapper mapper = configContext.getBean(MyBatisMapper.class);

    // Employees mapperRes = mapper.mybatisfindById(1L);
    // System.out.println(mapperRes);

    // Employees jpaRes = employeeRepository.findById(1L).orElse(null);
    // if (jpaRes != null) System.out.println(jpaRes.toString());

    Employees jpaRes = employeeRepository.mybatisfindById(1L);
    if (jpaRes != null) System.out.println(jpaRes.toString());
  }
}
