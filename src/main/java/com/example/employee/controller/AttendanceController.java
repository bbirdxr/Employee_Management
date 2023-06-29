package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ErrorCode;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Attendance;
import com.example.employee.exception.BusinessException;
import com.example.employee.model.dto.AttendanceQuery;
import com.example.employee.service.AttendanceService;
import com.example.employee.service.impl.AttendanceServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value="/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceServiceImpl attendanceService;

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

    @PostMapping("insert")
    public BaseResponse insert(@RequestBody Attendance attendance) {
        attendanceService.insert(attendance);
        return ResultUtils.success(true);
    }

    @PostMapping("deleteById/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (attendanceService.deleteById(id) == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }
}

