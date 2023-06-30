package com.example.employee.service.impl;

import com.example.employee.common.ErrorCode;
import com.example.employee.entity.Attendance;
import com.example.employee.entity.Employee;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.AttendanceMapper;
import com.example.employee.model.dto.AttendanceQuery;
import com.example.employee.service.AttendanceService;
import com.example.employee.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private EmployeeService employeeService;

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
    public int clockIn(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Employee employee = employeeService.selectById(employeeId);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        AttendanceQuery attendanceQuery = new AttendanceQuery();
        attendanceQuery.setEmployeeId(employeeId);
        attendanceQuery.setAttendanceDate(LocalDate.now());
        List<Attendance> attendances = attendanceMapper.selectByAttendanceQuery(attendanceQuery);
        if (attendances.size() > 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工已打卡");
        }
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setDepartmentId(employee.getDepartmentId());
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setClockInTime(new Date());
        return attendanceMapper.insert(attendance);
    }

    @Override
    public int clockOut(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Employee employee = employeeService.selectById(employeeId);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        AttendanceQuery attendanceQuery = new AttendanceQuery();
        attendanceQuery.setEmployeeId(employeeId);
        attendanceQuery.setAttendanceDate(LocalDate.now());
        List<Attendance> attendances = attendanceMapper.selectByAttendanceQuery(attendanceQuery);
        if (attendances == null || attendances.size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工未打卡");
        }
        Attendance attendance = attendances.get(0);
        attendance.setClockOutTime(new Date());
        return attendanceMapper.updateById(attendance);
    }


    @Override
    public int deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return attendanceMapper.deleteById(id);
    }
}
