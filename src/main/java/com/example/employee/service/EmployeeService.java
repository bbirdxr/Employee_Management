package com.example.employee.service;

import com.example.employee.entity.Employee;

import java.util.List;

public interface EmployeeService {
    //分页查询基本信息
    List<Employee> getAllEmployee();
    //查询员工公司就职历史
}
