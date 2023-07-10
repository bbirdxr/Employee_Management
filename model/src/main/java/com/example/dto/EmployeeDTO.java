package com.example.dto;

import com.example.entity.Employee;
import lombok.Data;

@Data
public class EmployeeDTO extends Employee {
    private String departmentPathName;
    private String positionName;
}
