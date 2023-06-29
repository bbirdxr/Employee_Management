package com.example.employee.service.impl;

import com.example.employee.common.ErrorCode;
import com.example.employee.entity.Employee;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.EmployeeMapper;
<<<<<<< HEAD
import com.example.employee.model.dto.EmployeeDTO;
import com.example.employee.service.EmployeeService;
=======
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.example.employee.model.dto.EmployeeDto;
>>>>>>> 98b352d4afefe666a983382e4901bd8f857c95db
import com.example.employee.service.PositionService;
import com.example.employee.util.MapObjectUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
=======
>>>>>>> 98b352d4afefe666a983382e4901bd8f857c95db

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        // 如果有缓存，直接读缓存
        Map<String, Object> resultMap = hashOperations.entries("redisKey");
        Employee employee;
        if (resultMap.size() > 0) {
            try {
                employee = MapObjectUtil.mapToObject(resultMap, Employee.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return employee;
        }
        // 无缓存，查数据库
        employee = employeeMapper.selectByName(name);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        // 写缓存
        Map<String, Object> employeeMap;
        try {
            employeeMap = MapObjectUtil.objectToMap(employee);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            hashOperations.putAll(redisKey, employeeMap);
            redisTemplate.expire(redisKey, 5, TimeUnit.MINUTES);
            System.out.println(hashOperations.entries(redisKey));
        } catch (Exception e) {
            System.out.println("redis set key error");
            // log.error("redis set key error", e);
        }
        return employee;
    }

    public EmployeeDTO findById(Long employId) {
        return toEmployeeDTO(employeeMapper.findByIdWithSalary(employId));
    }

    @Override
    public List<EmployeeDTO> selectByName(String EmployeeName) {
        List<Employee>ls=employeeMapper.findByName(EmployeeName);
        return toEmployeeDTOList(ls);
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

    public List<EmployeeDTO> toEmployeeDTOList(List<Employee>employees){
        List<EmployeeDTO> dtos= Lists.transform(employees, (entity)->{
            EmployeeDTO d = new EmployeeDTO();
            BeanUtils.copyProperties(entity, d);
            return d;
        });
        return dtos;
    }

    public EmployeeDTO toEmployeeDTO(Employee employee){
        EmployeeDTO dto=new EmployeeDTO();
        BeanUtils.copyProperties(employee,dto);
        // dto.setPositionName(positionService.getPositionNameById(employee.getPositionId()));
        // dto.setPositionName(positionService.getPositionNameById(employee.getPositionId()));
        return dto;
    }
}
