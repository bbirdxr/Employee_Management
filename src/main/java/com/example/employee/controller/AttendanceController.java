package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Attendance;
import com.example.employee.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 考勤表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/selectById")
    public BaseResponse<Attendance> selectById(Long id) {
        return ResultUtils.success(attendanceService.selectById(id));
    }
}

