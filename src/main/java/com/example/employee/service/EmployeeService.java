package com.example.employee.service;

import com.example.employee.entity.Employee;

public interface EmployeeService {
    Employee selectById(Long id);

    Employee selectByName(String name);
}
