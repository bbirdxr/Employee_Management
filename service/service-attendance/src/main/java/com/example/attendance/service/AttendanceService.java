package com.example.attendance.service;

import com.example.dto.AttendanceQuery;
import com.example.employee.entity.Attendance;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AttendanceService {
    Attendance selectById(Long id);

    List<Attendance> selectByAttendanceQuery(AttendanceQuery attendanceQuery);

    int clockIn(Long employeeId);

    int clockOut(Long employeeId);

    int deleteById(Long id);

    void exportAttendanceRecords(Long employeeId, HttpServletResponse response);
}
