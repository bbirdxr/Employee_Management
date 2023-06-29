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
    public boolean ifDepartmentExist(Long departmentId) {
        Department d=departmentMapper.selectByDepartmentId(departmentId);
        if(d==null)return true;
        else return false;
    }

    @Override
    public void deleteDepartmentIfExist(Long departmentId) {
        Department department=departmentMapper.selectByDepartmentId(departmentId);
        department.setSonDepartments(departmentMapper.selectByParentDepartmentId(department.getId()));
        if(!department.getSonDepartments().isEmpty()){
            for(Department d:department.getSonDepartments()){
                deleteDepartmentIfExist(d.getId());
            }
        }else {
            departmentMapper.deleteOneByDepartmentId(department.getId());
        }
    }

    @Override
    public Department findDepartment(String department) {
        Department d=departmentMapper.selectByDepartmentName(department);
        return d;
    }

    @Override
    public Department findDepartmentById(Long departmentId) {
        Department d=departmentMapper.selectByDepartmentId(departmentId);
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
    public void addDepartment(String departmentName, Long parentDepartmentId) {
        Long parentId=departmentMapper.selectByDepartmentId(parentDepartmentId).getId();
        departmentMapper.addDepartment(departmentName,parentId);
    }

    @Override
    public void addRootDepartment(String departmentName) {
        departmentMapper.addDepartment(departmentName,0L);
    }

    @Override
    public Department selectById(Long id) {
        return null;
    }

    public boolean ifReplicateName(String departmentName, Long parentDepartmentId) {
        //同一个部门下不能有重名的部门
        List<Department>departments=departmentMapper.selectByParentDepartmentId(parentDepartmentId);
        for (Department d:departments){
            if(d.getDepartmentName().equals(departmentName)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void changeDepartmentName(String newName,Long departmentId) {
        departmentMapper.updateDepartmentName(newName,departmentId);
    }

    @Override
    public String toString(Long departmentId) {
        Department d=departmentMapper.selectByDepartmentId(departmentId);
        String str=d.getDepartmentName();
        return appendDepartment(str,d);
    }

    public String appendDepartment(String str,Department d){
        if(d.getParentDepartmentId()==0L){
            return str;
        }else {
            Department dd=departmentMapper.selectByDepartmentId(d.getParentDepartmentId());
            str=dd.getDepartmentName()+"/"+str;
            appendDepartment(str,dd);
        }
        return str;
    }
}
