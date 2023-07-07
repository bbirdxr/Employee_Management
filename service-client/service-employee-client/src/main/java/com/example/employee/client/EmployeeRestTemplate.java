package com.example.employee.client;

import com.example.entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public interface EmployeeRestTemplate {
    Employee selectById(@PathVariable Long id);
}
