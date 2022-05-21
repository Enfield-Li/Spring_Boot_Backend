package com.example.reddit.employee.repository.custom;

import java.util.List;

import com.example.reddit.employee.Employees;
import com.example.reddit.employee.dto.EmployeeName;
import com.example.reddit.employee.repository.MyBatisMapper;

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
  public EmployeeName mybatisFindById(long id) {
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
