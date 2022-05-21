package com.example.reddit.employee;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MyBatisMapper {
  @Select("select * from employees")
  public List<Employees> mybatisFindAll();

  @Select("SELECT * FROM employees WHERE id = #{id}")
  public Employees mybatisFindById(long id);

  @Delete("DELETE FROM employees WHERE id = #{id}")
  public int mybatisDeleteById(long id);

  @Insert(
    "INSERT INTO employees(id, first_name, last_name,email_address) " +
    " VALUES (#{id}, #{firstName}, #{lastName}, #{emailId})"
  )
  public int mybatisInsert(Employees employee);

  @Update(
    "Update employees set first_name=#{firstName}, " +
    " last_name=#{lastName}, email_address=#{emailId} where id=#{id}"
  )
  public int mybatisUpdate(Employees employee);
}
