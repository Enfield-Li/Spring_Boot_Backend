package com.example.reddit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

    MyBatisMapper mapper = configContext.getBean(MyBatisMapper.class);

    Employees mapperRes = mapper.mybatisfindById(1L);
    System.out.println(mapperRes);

    Employees jpaRes = employeeRepository.findById(1L).orElse(null);
    if (jpaRes != null) System.out.println(jpaRes.toString());
  }
}
// logger.info(
//   "Inserting -> {}",
//   employeeRepository.insert(
//     new Employee(10011L, "Ramesh", "Fadatare", "ramesh@gmail.com")
//   )
// );
// logger.info(
//   "Inserting -> {}",
//   employeeRepository.insert(
//     new Employee(10012L, "John", "Cena", "john@gmail.com")
//   )
// );
// logger.info(
//   "Inserting -> {}",
//   employeeRepository.insert(
//     new Employee(10013L, "tony", "stark", "stark@gmail.com")
//   )
// );
// logger.info("Employee id 10011 -> {}", employeeRepository.findById(10011L));
// logger.info(
//   "Update 10003 -> {}",
//   employeeRepository.update(
//     new Employee(10011L, "ram", "Stark", "ramesh123@gmail.com")
//   )
// );
// employeeRepository.deleteById(10013L);
// logger.info("All users -> {}", employeeRepository.findAll());
// for (Employee employee : employeeRepository.findAll()) {
//   System.out.println(employee.toString());
// }
//     new Employee(10013L, "tony", "stark", "stark@gmail.com")
//   )
// );
// logger.info("Employee id 10011 -> {}", employeeRepository.findById(10011L));
// logger.info(
//   "Update 10003 -> {}",
//   employeeRepository.update(
//     new Employee(10011L, "ram", "Stark", "ramesh123@gmail.com")
//   )
// );
// employeeRepository.deleteById(10013L);
// logger.info("All users -> {}", employeeRepository.findAll());
// for (Employee employee : employeeRepository.findAll()) {
//   System.out.println(employee.toString());
// }
