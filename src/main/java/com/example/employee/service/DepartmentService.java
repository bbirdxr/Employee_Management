package com.example.employee.service;

import com.example.employee.entity.Department;
import com.example.employee.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DepartmentService {
    boolean ifDepartmentExist(Long departmentId);

    Department selectById(Long id);

    void deleteDepartmentIfExist(Long departmentId);

    Department findDepartment(String departmentName);

    Department findDepartmentById(Long departmentId);

    Department filledWithSons(Department department);
    void addDepartment(String departmentName,Long parentDepartmentId);
    void addRootDepartment(String department);
    boolean ifReplicateName(String departmentName,Long parentDepartmentId);
    void changeDepartmentName(String newName,Long departmentId);
    String toString(Long departmentId);
}