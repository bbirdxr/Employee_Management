package com.example.employee.client.impl;

import com.example.employee.client.EmployeeRestTemplate;
import com.example.entity.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeRestTemplateImpl implements EmployeeRestTemplate {

    @Override
    public Employee selectById(Long id) {
        String url = "http://localhost:8081/employee/inner/{id}";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Employee.class, id);
    }
}
