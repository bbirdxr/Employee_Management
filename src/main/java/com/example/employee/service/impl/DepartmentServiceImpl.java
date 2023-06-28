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

    }

    @Override
    public void deleteDepartmentIfExist(String departmentName) {
        Department department=departmentMapper.selectByDepartmentName(departmentName);
        department.setSonDepartments(departmentMapper.selectByParentDepartmentId(department.getId()));
        if(department.getSonDepartments()!=null){
            for(Department d:department.getSonDepartments()){

            }
        }else {
            departmentMapper.selectByDepartmentName(departmentName);
        }

        return department;
    }

    @Override
    public Department findDepartment(String department) {
        Department d=departmentMapper.selectByDepartmentName(department);
        return d;
    }

    @Override
    public Department filledWithSons(Department department) {
        department.setSonDepartments(departmentMapper.selectByParentDepartmentId(department.getId()));
        for(Department d:department.getSonDepartments()){
            filledWithSons(d);
        }
        return department;
    }

    @Override
    public void addDepartment(String departmentName, String parentDepartmentName) {
        Long parentId=departmentMapper.selectByDepartmentName(parentDepartmentName).getId();
        departmentMapper.addDepartment(departmentName,parentId);
    }

    @Override
    public void addRootDepartment(String departmentName) {
        departmentMapper.addDepartment(departmentName,0L);
    }
}
