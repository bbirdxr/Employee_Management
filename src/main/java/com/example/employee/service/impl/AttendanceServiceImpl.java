package com.example.employee.service.impl;

import com.example.employee.common.ErrorCode;
import com.example.employee.entity.Attendance;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.AttendanceMapper;
import com.example.employee.model.dto.AttendanceQuery;
import com.example.employee.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public Attendance selectById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return attendanceMapper.selectById(id);
    }

    @Override
    public List<Attendance> selectByAttendanceQuery(AttendanceQuery attendanceQuery) {
        if (attendanceQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return attendanceMapper.selectByAttendanceQuery(attendanceQuery);
    }

    @Override
    public int insert(Attendance attendance) {
        if (attendance == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return attendanceMapper.insert(attendance);
    }

    @Override
    public int deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return attendanceMapper.deleteById(id);
    }
}
