package com.example.reddit.user.repository.dao;

import com.example.reddit.mapper.source.userPost.UserPostInfoWithoutInteractions;

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

  public UserPostInfoWithoutInteractions doStuff() {
    this.sqlSession.selectOne("arg0", "arg1");
    return null;
  }
}
