package com.example.employee.service;

import com.example.employee.entity.Employee;

import com.example.employee.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    Employee selectById(Long id);

    Employee selectByNameSimple(String name);

    //分页查询基本信息

    List<Employee> pageSelectAllEmployee(int pageNum, int pageSize);

    EmployeeDto findById(Long employId);

    List<EmployeeDto>selectByName(String EmployeeName);

    void update(Employee employee);

    void updateSingleField(Long id, String field, Object value);

    void deleteById(Long employeeId);

    void deleteByDepartmentId(Long departmentId);

    void add(Employee employee);
}
