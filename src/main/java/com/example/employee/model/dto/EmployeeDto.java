package com.example.employee.model.dto;

import com.example.employee.entity.Department;
import com.example.employee.entity.Employee;
import com.example.employee.service.PositionService;
import com.example.employee.service.impl.DepartmentServiceImpl;
import com.example.employee.service.impl.EmployeeServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EmployeeDto extends Employee{

    private String departmentPathName;

    private String positionName;
}
