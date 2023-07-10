package com.example.employee.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.dto.EmployeeDTO;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.PositionService;
import com.example.entity.Employee;
import com.example.exception.BusinessException;
import com.example.result.ErrorCode;
import com.example.util.MapObjectUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.rocketmq.common.message.Message;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "employees")
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    DepartmentServiceImpl departmentService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private DefaultMQProducer defaultMQProducer;


    @Override
    // @Cacheable(key = "#p0")
    public Employee selectById(Long id) {
        if (id == null || id < 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "员工id不能为空");
        }
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
                log.error("redis set key error", e);
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

    // @CachePut(key = "#p0.id")
    public Employee add(Employee employee) {
        // catch database error
        try {
            employeeMapper.addNewEmployee(employee);
            employee = selectById(employee.getId());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库错误");
        }
        Message message = new Message("topic", "employee", JSON.toJSONString(employee).getBytes());
        try {
            SendResult sendResult = defaultMQProducer.send(message); // 同步消息
            log.info("Sending State: " + sendResult.getSendStatus() +
                    ", Message ID: " + sendResult.getMsgId() +
                    ", Message Queue: " + sendResult.getMessageQueue().getQueueId());
        } catch (RemotingException | MQBrokerException | InterruptedException | MQClientException e) {
            log.error("发送消息失败", e);
        }
        return employee;
    }
}
