package com.example.employee.mapper;

import com.example.employee.entity.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

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
    List<Employee> findAll();

    @Select("select id,name,email,phone_number,hire_date,create_time,update_time from employee where id=#{id}")
    Employee findByIdWithoutSalary(Long id);

    @Select("select * from employee where id=#{id}")
    Employee findByIdWithSalary(Long id);

    //最左匹配查询名字
    @Select("select * from employee where name like concat(#{name},'%')")
    List<Employee> findByName(String name);

    @Select("select * from employee,employee_department where department_id=#{departmentId} and employee_id=employee.id")
    List<Employee>findByDepartmentId(Long departmentId);

    @Insert("insert into employee" +
            "(name,email,phone_number,hire_date,salary,level,position_id,department_id) values" +
            "(#{e.name},#{e.phone_number},#{e.hire_date},#{e.salary},#{e.level},#{e.position_id},#{e.department_id})")
    void addNewEmployee(Employee e);

    @Update("update employee set ${field}=#{value} where id=#{id}")
    void update(Employee e);

    @Delete("delete from employee where id=#{id}")
    void deleteOneById(Long id);


    Employee selectByName(String name);

}
