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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "employees")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    DepartmentServiceImpl departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public static final String CACHE_KEY_USER="employ_id:";

    @Override
    @Cacheable(key = "#p0")
    public Employee selectById(Long id) {
        return employeeMapper.findByIdWithSalary(id);
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


    public EmployeeDTO toEmployeeDTO(Employee employee){
        EmployeeDTO dto=new EmployeeDTO();
        BeanUtils.copyProperties(employee,dto);
        dto.setPositionName(positionService.getPositionNameById(employee.getPositionId()));
        dto.setDepartmentPathName(departmentService.toString(employee.getDepartmentId()));
        return dto;
    }

    public List<EmployeeDTO> toEmployeeDTOList(List<Employee>employees){
        List<EmployeeDTO> dtos= Lists.transform(employees, (entity)->{
            EmployeeDTO d = new EmployeeDTO();
            BeanUtils.copyProperties(entity, d);
            d.setPositionName(positionService.getPositionNameById(entity.getPositionId()));
            d.setDepartmentPathName(departmentService.toString(entity.getDepartmentId()));
            return d;
        });
        return dtos;
    }

    @Override
    public List<EmployeeDTO> selectWithCondition(Employee employee){
        List<Employee>employeeList=employeeMapper.findAllWithCondition(employee);
        return toEmployeeDTOList(employeeList);
    }

    @Override
    @CachePut(key = "#p0.id")
    public Employee update(Employee employee) {
        employeeMapper.update(employee);
        return employee;
    }

    @Override
    public void updateSingleField(Long id, String field, Object value){
        employeeMapper.updateSingleField(id,field,value);
    }

    @Override
    @CacheEvict(key = "#p0", allEntries = true)
    public void deleteById(Long employeeId) {
        employeeMapper.deleteOneById(employeeId);
    }

    @CachePut(key = "#p0.id")
    public Employee add(Employee employee) {
        Long generateId=employeeMapper.addNewEmployee(employee);
        return selectById(employee.getId());
    }
}
