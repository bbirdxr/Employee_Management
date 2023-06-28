package com.example.employee.mapper;

import com.example.employee.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 员工信息 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Mapper
public interface EmployeeMapper {


    @Select("select * from employee")
    public List<Employee> findAll();

}
