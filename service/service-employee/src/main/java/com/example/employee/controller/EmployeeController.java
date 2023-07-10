package com.example.employee.controller;
import com.alibaba.fastjson.JSON;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.service.EmployeeService;
import com.example.entity.Employee;
import com.example.result.BaseResponse;
import com.example.result.ErrorCode;
import com.example.result.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.websocket.SendResult;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

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
        log.info("successfully insert an employee:"+employee.getName());
        return ResultUtils.success("true");
    }

    @PutMapping(value = "/person/{id}/{field}/{value}")//精确
    BaseResponse updateSingleField(@PathVariable Long id,@PathVariable String field,@PathVariable Object value){
        try {
            Employee.class.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            log.debug("Employee class does not have a field:"+field);
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"没有该字段");
        }catch (Exception e){//二级catch处理未知异常
            log.error("internal error:"+e.getMessage());
        }

        Employee e=employeeService.selectById(id);
        if(e==null)
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"no employee id:"+id);
        employeeService.updateSingleField(id,field,value);
        log.info("successfully update the Employee: id: "+id+" field:"+field+" value:"+value);
        return ResultUtils.success("successfully update the field");
    }


    @GetMapping("/name/{name}")
    public BaseResponse<List<Employee>> selectByName(@PathVariable String name) {
        return ResultUtils.success(employeeService.selectByNameSimple(name));
    }

    @DeleteMapping("/{id}")
    BaseResponse deleteById(@PathVariable Long id){
        if(employeeService.selectById(id)!=null){
            employeeService.deleteById(id);
            log.info("successfully delete employee id:"+id);
            return ResultUtils.success("delete successfully");
        }else {
            log.debug("delete employee failed: does not exsit employee id:"+id);
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"the user does not exsit");
        }
    }

    @GetMapping("/inner/{id}")
    public Employee selectById(@PathVariable Long id) {
        return employeeService.selectById(id);
    }
}

