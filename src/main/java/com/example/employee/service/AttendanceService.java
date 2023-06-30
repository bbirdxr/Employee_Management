package com.example.employee.service;

import com.example.employee.entity.Attendance;
import com.example.employee.model.dto.AttendanceQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AttendanceService {
    Attendance selectById(Long id);

    List<Attendance> selectByAttendanceQuery(AttendanceQuery attendanceQuery);

    int clockIn(Long employeeId);

    int clockOut(Long employeeId);

    int deleteById(Long id);
}
