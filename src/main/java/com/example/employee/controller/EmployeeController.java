package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.entity.Employee;
import com.example.employee.model.dto.EmployeeDto;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    @GetMapping("/")
    BaseResponse findAllByPage(){
        return null;
    }

    @PostMapping("/")
    BaseResponse add(@RequestBody Employee employee){
        return null;
    }

    @DeleteMapping("/")
    BaseResponse deleteById(@RequestParam Long employeeId){
        return null;
    }

    @DeleteMapping("/")
    BaseResponse deleteByName(@RequestParam String employeeName){
        return null;
    }

}

