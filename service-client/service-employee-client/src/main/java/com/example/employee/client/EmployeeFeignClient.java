package com.example.employee.client;

import com.example.entity.Employee;
import com.example.result.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "localhost:8081", name = "service-employee")
public interface EmployeeFeignClient {

    @PostMapping("/person")
    BaseResponse add(@RequestBody Employee employee);

    @GetMapping("/employee/inner/{id}")
    Employee selectById(@PathVariable Long id);
}
