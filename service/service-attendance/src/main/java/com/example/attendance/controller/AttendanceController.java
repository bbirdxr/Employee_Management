package com.example.attendance.controller;


import com.example.attendance.service.AttendanceService;
import com.example.dto.AttendanceQuery;
import com.example.entity.Attendance;
import com.example.exception.BusinessException;
import com.example.result.BaseResponse;
import com.example.result.ErrorCode;
import com.example.result.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 考勤表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@RestController
@RequestMapping( "/attendance")
@Slf4j
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("selectById/{id}")
    public BaseResponse<Attendance> selectById(@PathVariable Long id) {
        return ResultUtils.success(attendanceService.selectById(id));
    }

    @PostMapping("selectByAttendanceQuery/{pageNum}/{pageSize}")
    public BaseResponse<PageInfo<Attendance>> selectByAttendanceQuery(
            @PathVariable Integer pageNum,
            @PathVariable Integer pageSize,
            @RequestBody AttendanceQuery attendanceQuery) {
        PageHelper.startPage(pageNum, pageSize);
        List<Attendance> list = attendanceService.selectByAttendanceQuery(attendanceQuery);
        PageInfo<Attendance> pageInfo = new PageInfo<>(list);
        return ResultUtils.success(pageInfo);
    }

    @PostMapping("clockIn/{employeeId}")
    public BaseResponse clockIn(@PathVariable Long employeeId) {
        attendanceService.clockIn(employeeId);
        return ResultUtils.success("打卡成功");
    }

    @PostMapping("clockOut/{employeeId}")
    public BaseResponse clockOut(@PathVariable Long employeeId) {
        attendanceService.clockOut(employeeId);
        return ResultUtils.success("打卡成功");
    }

    @DeleteMapping("deleteById/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (attendanceService.deleteById(id) == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除失败");
        }
        return ResultUtils.success("删除成功");
    }

    @GetMapping("exportAttendanceRecords/{employeeId}")
    public BaseResponse exportAttendanceRecords(@PathVariable Long employeeId, HttpServletResponse response) {
        attendanceService.exportAttendanceRecords(employeeId, response);
        return ResultUtils.success("导出成功");
    }
}

