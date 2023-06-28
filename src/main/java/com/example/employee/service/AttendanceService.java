package com.example.employee.service;

import com.example.employee.entity.Attendance;
import com.example.employee.model.dto.AttendanceQuery;

import java.util.List;

public interface AttendanceService {
    Attendance selectById(Long id);

    List<Attendance> selectByAttendanceQuery(AttendanceQuery attendanceQuery);

    int insert(Attendance attendance);

    int deleteById(Long id);
}
