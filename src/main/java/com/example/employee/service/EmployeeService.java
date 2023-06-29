package com.example.employee.service;

import com.example.employee.entity.Employee;

import com.example.employee.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    Employee selectById(Long id);

    Employee selectByNameSimple(String name);

    List<EmployeeDto> selectWithCondition(Employee employee);

    void update(Employee employee);

    void updateSingleField(Long id, String field, Object value);

    void deleteById(Long employeeId);

    void add(Employee employee);
}
