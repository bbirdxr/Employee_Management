package com.example.employee.service.impl;

import com.example.employee.entity.Employee;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.model.dto.EmployeeDto;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.PositionService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    DepartmentServiceImpl departmentService;

    @Autowired
    PositionService positionService;

    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public List<Employee> pageSelectAllEmployee(int pageNum, int pageSize) {
        return null;
    }

    @Override
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
