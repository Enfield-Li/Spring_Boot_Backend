package com.example.reddit;

import com.example.reddit.mybatis.User;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

public class MabatisTest {

  @Test
  public void test() throws IOException {
    String resource = "../main/resources/Mybatis.xml";

    InputStream inputStream = Resources.getResourceAsStream(resource);

    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
    .build(inputStream);

    try (SqlSession session = sqlSessionFactory.openSession()) {
      User user = session.selectOne(
        "com.example.reddit.resources.UserMapper.selectUser",
        1
      );

      System.out.println(user.toString());
    }
  }
}
