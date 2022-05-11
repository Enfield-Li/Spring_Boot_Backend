package com.example.reddit.mapper.test.nested;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EmployeeMapper {
  @Mappings(
    {
      @Mapping(target = "employeeId", source = "entity.id"),
      @Mapping(target = "employeeName", source = "entity.name"),
      @Mapping(target = "division.id", source = "entity.divisionId"),
      @Mapping(target = "division.name", source = "entity.divisionName"),
    }
  )
  EmployeeDTO employeeToEmployeeDTO(Employee entity);
  //   @Mappings(
  //     {
  //       @Mapping(target = "id", source = "dto.employeeId"),
  //       @Mapping(target = "name", source = "dto.employeeName"),
  //     }
  //   )
  //   Employee employeeDTOtoEmployee(EmployeeDTO dto);
}
