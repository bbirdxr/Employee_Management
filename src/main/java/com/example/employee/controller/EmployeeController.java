package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ErrorCode;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.employee.entity.Employee;
import com.example.employee.model.dto.EmployeeDTO;
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

    @GetMapping("/all")
    BaseResponse findAll(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        
        return null;
    }

    @PostMapping("/person")
    BaseResponse add(@RequestBody Employee employee){
        employeeService.add(employee);
        return ResultUtils.success("true");
    }

    @PutMapping("/person/{id}/{field}/{value}")
    BaseResponse updateSingleField(@PathVariable Long id,@PathVariable String field,@PathVariable Object value){
        try {
            System.out.println(Employee.class.getDeclaredFields());
            Employee.class.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"没有该字段");
        }
        employeeService.updateSingleField(id,field,value);
        return ResultUtils.success("更新字段成功");
    }

    @DeleteMapping("/{id}")
    BaseResponse deleteById(@PathVariable Long employeeId){
        return null;
    }

    @GetMapping("/name/{name}")
    public BaseResponse<Employee> selectByName(@PathVariable String name) {
        return ResultUtils.success(employeeService.selectByNameSimple(name));
    }

}

