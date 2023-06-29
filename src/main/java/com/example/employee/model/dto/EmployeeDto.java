package com.example.employee.model.dto;

import com.example.employee.entity.Employee;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EmployeeDto{
    private Long id;
    private Long departmentId;
    private Long positionId;
    private String name;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private BigDecimal salary;
    private String departmentPathName;
    private String positionName;
}
