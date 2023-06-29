package com.example.employee.service;

import com.example.employee.entity.Employee;

import com.example.employee.model.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    Employee selectById(Long id);

    Employee selectByNameSimple(String name);

    //分页查询基本信息

    List<Employee> pageSelectAllEmployee(int pageNum, int pageSize);

    EmployeeDTO findById(Long employId);

    List<EmployeeDTO>selectByName(String EmployeeName);

    void update(Employee employee);

    void deleteById(Long employeeId);

    void deleteByDepartmentId(Long departmentId);

    void add(Employee employee);
}
