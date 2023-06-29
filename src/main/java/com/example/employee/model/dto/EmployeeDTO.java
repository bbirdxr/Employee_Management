package com.example.employee.model.dto;

import com.example.employee.entity.Employee;
import lombok.Data;

@Data
public class EmployeeDTO extends Employee{
    private String departmentPathName;
    private String positionName;
}
