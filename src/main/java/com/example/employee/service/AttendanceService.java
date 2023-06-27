package com.example.employee.service;

import com.example.employee.entity.Attendance;

public interface AttendanceService {
    Attendance selectById(Long id);
}
