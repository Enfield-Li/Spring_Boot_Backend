package com.example.reddit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Mapper
@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {}
/* 
create table employees
(
   id integer not null,
   first_name varchar(255) not null, 
   last_name varchar(255) not null,
   email_address varchar(255) not null,
   primary key(id)
);
 */
