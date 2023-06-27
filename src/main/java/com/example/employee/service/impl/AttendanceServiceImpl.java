package com.example.employee.service.impl;

import com.example.employee.entity.Attendance;
import com.example.employee.mapper.AttendanceMapper;
import com.example.employee.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public Attendance selectById(Long id) {
        return attendanceMapper.selectById(id);
    }
}
