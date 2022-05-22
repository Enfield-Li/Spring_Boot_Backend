package com.example.reddit;

import com.example.reddit.mapper.HotelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedditApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(
      RedditApplication.class,
      args
    );

    HotelMapper mapper = ctx.getBean(HotelMapper.class);

    System.out.println(mapper.selectByCityId(1));
    System.out.println(mapper.selectAll().size());
  }
}
