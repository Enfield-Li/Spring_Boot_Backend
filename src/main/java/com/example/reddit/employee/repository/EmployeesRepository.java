package com.example.reddit.employee.repository;

import com.example.reddit.employee.Employees;
import com.example.reddit.employee.repository.custom.CustomizedRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository
  extends JpaRepository<Employees, Long>, CustomizedRepository {}
