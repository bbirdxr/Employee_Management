package com.example.employee.service.impl;

import com.example.employee.entity.Employee;
import com.example.employee.model.dto.EmployeeDto;
import com.example.employee.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public List<Employee> pageSelectAllEmployee(int pageNum, int pageSize) {
        return null;
    }

    @Override
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
        EmployeeDto ed=new EmployeeDto();

    }
}
