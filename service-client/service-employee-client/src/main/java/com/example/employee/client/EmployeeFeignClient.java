package com.example.employee.client;

import com.example.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "localhost:8081", name = "service-employee")
public interface EmployeeFeignClient {
    @GetMapping("/employee/inner/{id}")
    Employee selectById(@PathVariable Long id);
}
