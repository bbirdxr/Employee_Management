package com.example.attendance.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.attendance.mapper.AttendanceMapper;
import com.example.attendance.service.AttendanceService;
import com.example.dto.AttendanceQuery;
import com.example.employee.client.EmployeeFeignClient;
import com.example.employee.client.EmployeeRestTemplate;
import com.example.entity.Attendance;
import com.example.entity.Employee;
import com.example.exception.BusinessException;
import com.example.result.ErrorCode;
import com.example.vo.AttendanceVO;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private EmployeeFeignClient employeeFeignClient;

    @Autowired
    private EmployeeRestTemplate employeeRestTemplate;

    @Override
    public Attendance selectById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return attendanceMapper.selectById(id);
    }

    @Override
    public List<Attendance> selectByAttendanceQuery(AttendanceQuery attendanceQuery) {
        if (attendanceQuery != null) {
            if (attendanceQuery.getEmployeeId() != null && attendanceQuery.getEmployeeId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            if (attendanceQuery.getDepartmentId() != null && attendanceQuery.getDepartmentId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            if (attendanceQuery.getAttendanceDate() != null && attendanceQuery.getAttendanceDate().isAfter(LocalDate.now())) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        return attendanceMapper.selectByAttendanceQuery(attendanceQuery);
    }

    @Override
    public int clockIn(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Employee employee;
        try {
            employee = employeeRestTemplate.selectById(employeeId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        AttendanceQuery attendanceQuery = new AttendanceQuery();
        attendanceQuery.setEmployeeId(employeeId);
        attendanceQuery.setAttendanceDate(LocalDate.now());
        List<Attendance> attendances = attendanceMapper.selectByAttendanceQuery(attendanceQuery);
        if (attendances.size() > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "员工已打卡");
        }
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setDepartmentId(employee.getDepartmentId());
        attendance.setClockInTime(new Date());
        return attendanceMapper.insert(attendance);
    }

    @Override
    public int clockOut(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
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

    @Override
    public void exportAttendanceRecords(Long employeeId, HttpServletResponse response) {
//        if (employeeId == null || employeeId <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
        Employee employee;
        try {
            employee = employeeFeignClient.selectById(employeeId);
        } catch(RetryableException e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName;
        try {
            fileName = URLEncoder.encode(employee.getName() + "的考勤记录", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        AttendanceQuery attendanceQuery = new AttendanceQuery();
        attendanceQuery.setEmployeeId(employeeId);
        List<Attendance> attendanceList = attendanceMapper.selectByAttendanceQuery(attendanceQuery);
        List<AttendanceVO> attendanceVOList = new ArrayList<>(attendanceList.size());
        for (Attendance attendance : attendanceList) {
            AttendanceVO attendanceVO = new AttendanceVO();
            BeanUtils.copyProperties(attendance, attendanceVO);
            attendanceVOList.add(attendanceVO);
        }
        try {
            EasyExcel.write(response.getOutputStream(), AttendanceVO.class).sheet("考勤记录").doWrite(attendanceVOList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
