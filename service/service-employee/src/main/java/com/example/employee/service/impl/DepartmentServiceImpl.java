<<<<<<< HEAD
package com.example.employee.service.impl;

import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.service.DepartmentService;
import com.example.entity.Department;
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
    public List<Department> getAllDepartment(){
        List<Department>departments=departmentMapper.selectByParentDepartmentId(0L);
        for(Department d:departments){
            filledWithSons(d);
        }
        return departments;
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
    public Department findDepartmentByParentIdAndName(Long parentDepartmentId,String departmentName) {
        Department d=departmentMapper.selectByParentDepartmentIdAndName(parentDepartmentId,departmentName);
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
        departmentMapper.addDepartment(departmentName,parentDepartmentId);
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
=======
package com.example.employee.service.impl;

import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.service.DepartmentService;
import com.example.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public boolean ifDepartmentExist(Long departmentId) {
        Department d=departmentMapper.selectByDepartmentId(departmentId);
        if(d==null)return true;
        else return false;
    }

    @Override
    public List<Department> getAllDepartment(){
        List<Department>departments=departmentMapper.selectByParentDepartmentId(0L);
        for(Department d:departments){
            filledWithSons(d);
        }
        return departments;
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
    public Department findDepartmentByParentIdAndName(Long parentDepartmentId,String departmentName) {
        Department d=departmentMapper.selectByParentDepartmentIdAndName(parentDepartmentId,departmentName);
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
        departmentMapper.addDepartment(departmentName,parentDepartmentId);
    }

    @Override
    public void addRootDepartment(String departmentName) {
        departmentMapper.addDepartment(departmentName,0L);
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
>>>>>>> ba0863a92bed74b938c097e7218c38a40a23c806
