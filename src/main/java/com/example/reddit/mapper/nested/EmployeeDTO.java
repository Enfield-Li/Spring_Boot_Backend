package com.example.reddit.mapper.nested;

import lombok.Data;

@Data
public class EmployeeDTO {

  private int employeeId;
  private String employeeName;
  private DivisionDTO division;
}
