package com.example.employee.client.impl;

import com.example.employee.client.EmployeeRestTemplate;
import com.example.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Retryable(value = RestClientException.class, maxAttempts = 3,
        backoff = @Backoff(delay = 5000L, multiplier = 2))
public class EmployeeRestTemplateImpl implements EmployeeRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Employee selectById(Long id) {
        String url = "http://localhost:8081/employee/inner/{id}";

        return restTemplate.getForObject(url, Employee.class, id);
    }
}
