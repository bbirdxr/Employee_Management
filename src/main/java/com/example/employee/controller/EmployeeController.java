package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.employee.entity.Employee;
import com.example.employee.model.dto.EmployeeDto;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

//    @GetMapping("/")
//    BaseResponse findAllByPage(){
//        return null;
//    }
//
//    @PostMapping("/")
//    BaseResponse add(@RequestBody Employee employee){
//        return null;
//    }
//
//    @DeleteMapping("/")
//    BaseResponse deleteById(@RequestParam Long employeeId){
//        return null;
//    }
//
//    @DeleteMapping("/")
//    BaseResponse deleteByName(@RequestParam String employeeName){
//        return null;
//    }

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("selectByName/{name}")
    public BaseResponse<Employee> selectByName(@PathVariable String name) {
        return ResultUtils.success(employeeService.selectByNameSimple(name));
    }

}

