package com.example.employee.service.impl;

import com.example.employee.entity.Department;
import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public boolean ifDepartmentExist(String departmentName) {
        Department d=departmentMapper.selectByDepartmentName(departmentName);
        if(d==null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void deleteDepartmentIfExist(Department department) {

    }

    @Override
    public List<Department> findDepartments(Department department) {
        return null;
    }
}
