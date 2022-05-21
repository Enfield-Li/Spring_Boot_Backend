package com.example.reddit.employee;

import java.util.List;

public interface CustomizedRepository {
  public List<Employees> mybatisFindAll();

  public Employees mybatisfindById(long id);

  public int mybatisDeleteById(long id);

  public int mybatisInsert(Employees employee);

  public int mybatisUpdate(Employees employee);
}
