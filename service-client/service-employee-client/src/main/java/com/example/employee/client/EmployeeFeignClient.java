package com.example.employee.client;

import com.example.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-employee")
public interface EmployeeFeignClient {
    @GetMapping("/employee/inner/selectById/{id}")
    Employee selectById(@PathVariable Long id);
}
