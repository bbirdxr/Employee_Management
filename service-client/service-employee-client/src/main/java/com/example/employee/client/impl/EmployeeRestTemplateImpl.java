package com.example.employee.client.impl;

import com.example.employee.client.EmployeeRestTemplate;
import com.example.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeRestTemplateImpl implements EmployeeRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Employee selectById(Long id) {
        String url = "http://localhost:8081/employee/inner/{id}";

        return restTemplate.getForObject(url, Employee.class, id);
        // authration: basic: sasdasdas
    }
}
