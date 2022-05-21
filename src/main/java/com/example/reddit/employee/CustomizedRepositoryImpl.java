package com.example.reddit.employee;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomizedRepositoryImpl implements CustomizedRepository {

  private MyBatisMapper employeeMapper;

  @Autowired
  CustomizedRepositoryImpl(MyBatisMapper employeeMapper) {
    this.employeeMapper = employeeMapper;
  }

  @Override
  public List<Employees> mybatisFindAll() {
    return employeeMapper.mybatisFindAll();
  }

  @Override
  public Employees mybatisFindById(long id) {
    return employeeMapper.mybatisFindById(id);
  }

  @Override
  public int mybatisDeleteById(long id) {
    return employeeMapper.mybatisDeleteById(id);
  }

  @Override
  public int mybatisInsert(Employees employee) {
    return employeeMapper.mybatisInsert(employee);
  }

  @Override
  public int mybatisUpdate(Employees employee) {
    return employeeMapper.mybatisUpdate(employee);
  }
}
