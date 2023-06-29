package com.example.employee.service.impl;

import com.example.employee.common.ErrorCode;
import com.example.employee.entity.Employee;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.example.employee.model.dto.EmployeeDto;
import com.example.employee.service.PositionService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    PositionService positionService;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


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
        return toEmployeeDto(employeeMapper.findByIdWithSalary(employId));
    }

    @Override
    public List<EmployeeDto> selectByName(String EmployeeName) {
        List<Employee>ls=employeeMapper.findByName(EmployeeName);
        return toEmployeeDtoList(ls);
    }

    @Override
    public void update(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    public void updateSingleField(Long id, String field, Object value){
        employeeMapper.updateSingleField(id,field,value);
    }

    @Override
    public void deleteById(Long employeeId) {

    }

    @Override
    public void deleteByDepartmentId(Long departmentId) {

    }


    @Override
    public void add(Employee employee) {
        employeeMapper.addNewEmployee(employee);
    }

    public List<EmployeeDto> toEmployeeDtoList(List<Employee>employees){
        List<EmployeeDto> dtos= Lists.transform(employees, (entity)->{
            EmployeeDto d = new EmployeeDto();
            BeanUtils.copyProperties(entity, d);
            return d;
        });
        return dtos;
    }

    public EmployeeDto toEmployeeDto(Employee employee){
        EmployeeDto dto=new EmployeeDto();
        BeanUtils.copyProperties(employee,dto);
        dto.setPositionName(positionService.getPositionNameById(employee.getPositionId()));
        dto.setPositionName(positionService.getPositionNameById(employee.getPositionId()));
        return dto;
    }
}
