package com.example.reddit.employee.repository.custom;

import java.util.List;

import com.example.reddit.employee.Employees;
import com.example.reddit.employee.dto.EmployeeName;

public interface CustomizedRepository {
  public List<Employees> mybatisFindAll();

  public EmployeeName mybatisFindById(long id);

  public int mybatisDeleteById(long id);

  public int mybatisInsert(Employees employee);

  public int mybatisUpdate(Employees employee);
}
