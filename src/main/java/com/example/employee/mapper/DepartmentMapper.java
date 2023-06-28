package com.example.employee.mapper;

import com.example.employee.entity.Department;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Mapper
public interface DepartmentMapper {
    @Insert("insert into department(department_name,parent_department_id) values(#{departmentName},#{parentDepartmentId}) ")
    void addDepartment(String departmentName,Long parentDepartmentId);

    @Select("select * from department where department_name=#{departmentName}")
    Department selectByDepartmentName(String departmentName);

    @Select("select * from department where parent_department_id=#{parentDepartmentId}")
    List<Department> selectByParentDepartmentId(Long parentDepartmentId);



}
