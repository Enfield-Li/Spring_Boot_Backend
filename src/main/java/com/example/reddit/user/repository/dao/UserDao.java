package com.example.reddit.user.repository.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

  private final SqlSession sqlSession;

  @Autowired
  public UserDao(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  public void doStuff() {
    this.sqlSession.selectOne("arg0", "arg1");
  }
}
