package com.example.reddit.user.repository;

import com.example.reddit.user.User;
import com.example.reddit.user.dto.UserInfo;
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

  public User findUserByIdWM(String useranme) {
    return this.sqlSession.selectOne("findUserByIdWM", useranme);
  }

  public UserInfo findUserInfoByIdWM(Long id) {
    return this.sqlSession.selectOne("findUserInfoByIdWM", id);
  }
}