package com.example.employee.mapper;

import com.example.entity.Department;
import org.apache.ibatis.annotations.*;

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

    @Select("select * from department where id=#{departmentId}")
    Department selectByDepartmentId(Long departmentId);

    @Select("select * from department where parent_department_id=#{parentDepartmentId} and department_name=#{departmentName}")
    Department selectByParentDepartmentIdAndName(Long parentDepartmentId,String departmentName);

    @Select("select * from department where parent_department_id=#{parentDepartmentId}")
    List<Department> selectByParentDepartmentId(Long parentDepartmentId);

    @Delete("delete from department where id=#{departmentId}")
    void deleteOneByDepartmentId(Long departmentId);

    @Update("update department set department_name=#{newName} where id=#{departmentId}")
    void updateDepartmentName(String newName,Long departmentId);
}
