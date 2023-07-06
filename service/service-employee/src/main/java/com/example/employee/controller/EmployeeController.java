package com.example.employee.controller;

import com.example.employee.mapper.EmployeeMapper;
import com.example.entity.Employee;
import com.example.result.BaseResponse;
import com.example.result.ErrorCode;
import com.example.result.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.employee.service.impl.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@RestController
@Slf4j
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EmployeeMapper employeeMapper;

    @PostMapping("/all")
    BaseResponse findAll(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageCount,
            @RequestBody Employee employee){
        PageHelper.startPage(pageNumber,pageCount);
        List<Employee>employeeList=employeeMapper.findAllWithCondition(employee);
        PageInfo<Employee>pageInfo=new PageInfo<>(employeeList);
//        PageInfo<EmployeeDTO>employeeDTOPageInfo=new PageInfo<>();
//        BeanUtils.copyProperties(pageInfo,employeeDTOPageInfo);

//        List<EmployeeDTO>dtos=employeeService.selectWithCondition(employee);
//        PageInfo<EmployeeDTO> pageInfo = new PageInfo<>(dtos);
        return ResultUtils.success(pageInfo);
    }

    @PostMapping("/person")
    BaseResponse add(@RequestBody Employee employee){
        employeeService.add(employee);
        return ResultUtils.success("true");
    }

    @PutMapping(value = "/person/{id}/{field}/{value}")//精确
    BaseResponse updateSingleField(@PathVariable Long id,@PathVariable String field,@PathVariable Object value){
        try {
            log.debug("获取Employee字段");
            Employee.class.getDeclaredField(field);
        } catch (NoSuchFieldException e) {//二级catch
            log.error("Employee对象没有"+field+"字段");
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"没有该字段");
        }
        employeeService.updateSingleField(id,field,value);
        log.info("更新Employee字段成功：id："+id+" field:"+field+" value:"+value);
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

    @GetMapping("/inner/selectById/{id}")
    public Employee selectById(@PathVariable Long id) {
        return employeeService.selectById(id);
    }
}

