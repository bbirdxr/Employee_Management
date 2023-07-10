package com.example.employee.service;

import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee selectById(Long id);

    List<Employee> selectByNameSimple(String name);

    void importDataToRedis();

    List<EmployeeDTO> selectWithCondition(Employee employee);

    //分页查询基本信息

    Employee update(Employee employee);

    void updateSingleField(Long id, String field, Object value);

    void deleteById(Long employeeId);

    Employee add(Employee employee);
}
