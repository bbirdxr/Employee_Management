package com.example.attendance;

import com.example.attendance.mapper.AttendanceMapper;
import com.example.dto.AttendanceQuery;
import com.example.entity.Attendance;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class AttendanceTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private AttendanceMapper attendanceMapper;

    @Before
    public void init() throws Exception{
        in = Resources.getResourceAsStream("mybatis-config.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
        attendanceMapper = session.getMapper(AttendanceMapper.class);
    }

    @After
    public void destroy() throws Exception{
        session.commit();
        session.close();
        in.close();
    }

    @Test
    public void testSelectById(){
        Attendance attendance = attendanceMapper.selectById(1L);
        System.out.println(attendance);
    }

    @Test
    public void testInsert(){
    }

    @Test
    public void testDeleteById(){
        System.out.println(attendanceMapper.deleteById(1L));
    }

    @Test
    public void testUpdateById(){
        Attendance attendance = attendanceMapper.selectById(2L);
        if (attendance != null) {
            attendance.setClockInTime(new java.util.Date());
        }
        System.out.println(attendanceMapper.updateById(attendance));
    }

    @Test
    public void testSelectByEmployeeIdAndAttendanceDate(){
        AttendanceQuery attendanceQuery = new AttendanceQuery();
        attendanceQuery.setDepartmentId(2L);
        List<Attendance> attendances = attendanceMapper.selectByAttendanceQuery(attendanceQuery);
        attendances.forEach(System.out::println);
    }
}