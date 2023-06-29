package com.example.employee.service.impl;

import com.example.employee.common.ErrorCode;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Employee;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.example.employee.entity.Employee;
import com.example.employee.model.dto.EmployeeDto;
import com.example.employee.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee selectById(Long id) {
        return null;
    }

    @Override
    public List<Employee> pageSelectAllEmployee(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Employee selectByNameSimple(String name) {
        String redisKey = String.format("employee:%s", name);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 如果有缓存，直接读缓存
        Employee employee = (Employee) valueOperations.get(redisKey);
        if (employee != null) {
            return employee;
        }
        // 无缓存，查数据库
        employee = employeeMapper.selectByName(name);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        // 写缓存
        try {
            valueOperations.set(redisKey, employee, 30000, TimeUnit.MILLISECONDS);
            System.out.println("写入缓存");
        } catch (Exception e) {
            // log.error("redis set key error", e);
        }
        return employee;
    }

    public EmployeeDto findById(Long employId) {
        return null;
    }

    @Override
    public List<EmployeeDto> selectByName(String EmployeeName) {
        return null;
    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    public void deleteById(Long employeeId) {

    }

    @Override
    public void deleteByDepartmentId(Long departmentId) {

    }

    @Override
    public void add(Employee employee) {

    }

    public EmployeeDto toEmployeeDto(Employee employee){
        EmployeeDto ed=new EmployeeDto(employee);
        return ed;
    }
}
