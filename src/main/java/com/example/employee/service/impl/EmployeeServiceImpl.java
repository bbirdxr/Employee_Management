package com.example.employee.service.impl;

import com.example.employee.common.ErrorCode;
import com.example.employee.entity.Employee;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.model.dto.EmployeeDTO;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.PositionService;
import com.example.employee.util.MapObjectUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private PositionService positionService;

    @Autowired
    private EmployeeMapper employeeMapper;

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
    public List<Employee> selectByNameSimple(String name) {
        String keyPattern = "employee:" + "*" + name + "*";
        Set<String> redisKeys = redisTemplate.keys(keyPattern);
        List<Employee> employeeList = Lists.newArrayList();
        for (String redisKey : redisKeys) {
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            Map<String, Object> resultMap = hashOperations.entries(redisKey);
            Employee employee;
            try {
                employee = MapObjectUtil.mapToObject(resultMap, Employee.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            employeeList.add(employee);
        }
        if (employeeList.isEmpty()) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        return employeeList;
    }

    @Override
    public void importDataToRedis() {
        List<Employee> employeeList = employeeMapper.findAll();
        for (Employee employee : employeeList) {
            String key = "employee:" + employee.getName() + employee.getId();
            HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
            Map<String, Object> employeeMap;
            try {
                employeeMap = MapObjectUtil.objectToMap(employee);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            try {
                hashOperations.putAll(key, employeeMap);
                // redisTemplate.expire(key, 5, TimeUnit.MINUTES);
                System.out.println(hashOperations.entries(key));
            } catch (Exception e) {
                System.out.println("redis set key error");
                // log.error("redis set key error", e);
            }
        }
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
        dto.setPositionName(positionService.getPositionNameById(employee.getPositionId()));
        dto.setPositionName(positionService.getPositionNameById(employee.getPositionId()));
        return dto;
    }
}
