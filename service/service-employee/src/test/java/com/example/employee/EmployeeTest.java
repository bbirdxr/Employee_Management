package com.example.employee;

import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.mapper.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;

/**
 * Employee 测试类
 */
public class EmployeeTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private EmployeeMapper employeeMapper;
    private DepartmentMapper departmentMapper;

    @Before
    public void init() throws Exception{
        in = Resources.getResourceAsStream("mybatis-config.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        employeeMapper = session.getMapper(EmployeeMapper.class);
        departmentMapper=session.getMapper(DepartmentMapper.class);
    }

    @After
    public void destroy() throws Exception{
        session.commit();
        session.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        System.out.println(departmentMapper.selectByDepartmentName("设计部"));
    }

}
