package com.example.employee.service;

import com.example.employee.entity.Department;
import com.example.employee.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DepartmentService {
    boolean ifDepartmentExist(String departmentName);
    void deleteDepartmentIfExist(Department department);
    List<Department> findDepartments(Department department);
}
