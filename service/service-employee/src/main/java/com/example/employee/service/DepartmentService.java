<<<<<<< HEAD
package com.example.employee.service;

import com.example.entity.Department;

import java.util.List;


public interface DepartmentService {
    boolean ifDepartmentExist(Long departmentId);

    Department selectById(Long id);

    List<Department> getAllDepartment();

    void deleteDepartmentIfExist(Long departmentId);

    Department findDepartmentByParentIdAndName(Long parentDepartmentId, String departmentName);

    Department findDepartmentById(Long departmentId);

    Department filledWithSons(Department department);
    void addDepartment(String departmentName,Long parentDepartmentId);
    void addRootDepartment(String department);
    boolean ifReplicateName(String departmentName,Long parentDepartmentId);
    void changeDepartmentName(String newName,Long departmentId);
    String toString(Long departmentId);
}
=======
package com.example.employee.service;

import com.example.entity.Department;

import java.util.List;


public interface DepartmentService {
    List<Department> getAllDepartment();

    void deleteDepartmentIfExist(Long departmentId);

    Department findDepartmentByParentIdAndName(Long parentDepartmentId, String departmentName);

    Department findDepartmentById(Long departmentId);

    Department filledWithSons(Department department);

    void addDepartment(String departmentName,Long parentDepartmentId);

    void addRootDepartment(String department);

    boolean ifReplicateName(String departmentName,Long parentDepartmentId);

    void changeDepartmentName(String newName,Long departmentId);

    String toString(Long departmentId);
}
>>>>>>> ba0863a92bed74b938c097e7218c38a40a23c806
