package com.example.employee.service;

import com.example.employee.entity.Employee;

import com.example.employee.model.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    Employee selectById(Long id);

    List<Employee> selectByNameSimple(String name);

    void importDataToRedis();

    List<EmployeeDTO> selectWithCondition(Employee employee);

    //分页查询基本信息

    EmployeeDTO findById(Long employId);



    void update(Employee employee);

    void updateSingleField(Long id, String field, Object value);

    void deleteById(Long employeeId);

    void add(Employee employee);
}
