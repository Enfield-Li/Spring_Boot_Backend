package com.example.reddit.post.repository;

import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostDao {

  private final SqlSession sqlSession;

  @Autowired
  public PostDao(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }
}
