package com.example.reddit;

import com.example.reddit.mapper.HotelMapper;
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

    HotelMapper hotelMapper = configContext.getBean(HotelMapper.class);

    System.out.println(hotelMapper.selectByCityId(1));
  }
}
