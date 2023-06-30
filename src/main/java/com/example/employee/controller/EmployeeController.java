package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ErrorCode;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Employee;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.employee.model.dto.EmployeeDTO;
import com.example.employee.service.impl.EmployeeServiceImpl;
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

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/all")
    BaseResponse findAll(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestBody Employee employee){
        PageHelper.startPage(pageNum,pageSize);
        List<EmployeeDTO>dtos=employeeService.selectWithCondition(employee);
        PageInfo<EmployeeDTO> pageInfo = new PageInfo<>(dtos);
        return ResultUtils.success(pageInfo);
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


    @GetMapping("/name/{name}")
    public BaseResponse<List<Employee>> selectByName(@PathVariable String name) {
        return ResultUtils.success(employeeService.selectByNameSimple(name));
    }

    @DeleteMapping("/{id}")
    BaseResponse deleteById(@PathVariable Long id){
        if(employeeService.selectById(id)!=null){
            employeeService.deleteById(id);
            return ResultUtils.success("删除成功");
        }else {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"不存在该用户");
        }
    }


    @GetMapping("/importDataToRedis")
    public BaseResponse importDataToRedis() {
        employeeService.importDataToRedis();
        return ResultUtils.success("导入成功");
    }
}

