package com.example.employee.mapper;

import com.example.entity.Employee;
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


    List<Employee>findAllWithCondition(Employee employee);

    @Select("select * from employee,employee_department where department_id=#{departmentId} and employee_id=employee.id")
    List<Employee>findByDepartmentId(Long departmentId);

    @Insert("insert into employee" +
            "(name,email,phone_number,hire_date,salary,level,position_id,department_id) values" +
            "(#{name},#{email},#{phoneNumber},#{hireDate},#{salary},#{level},#{positionId},#{departmentId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long addNewEmployee(Employee e);

    @Update("update employee set ${field}=#{value} where id=#{id}")
    void updateSingleField(Long id,String field,Object value);


    void update(Employee e);

    @Update("update employee set is_deleted=1  where id=#{id}")
    void deleteOneById(Long id);

}
