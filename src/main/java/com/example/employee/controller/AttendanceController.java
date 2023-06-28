package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Attendance;
import com.example.employee.model.dto.AttendanceQuery;
import com.example.employee.service.AttendanceService;
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
@RequestMapping(value="/attendance", produces = "application/json;charset=UTF-8")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("selectById/{id}")
    public BaseResponse<Attendance> selectById(@PathVariable Long id) {
        System.out.println(id);
        return ResultUtils.success(attendanceService.selectById(id));
    }

    @PostMapping("selectByAttendanceQuery")
    public BaseResponse<List<Attendance>> selectByAttendanceQuery(@RequestBody AttendanceQuery attendanceQuery) {
        return ResultUtils.success(attendanceService.selectByAttendanceQuery(attendanceQuery));
    }

    @PostMapping("insert")
    public BaseResponse insert(@RequestBody Attendance attendance) {
        attendanceService.insert(attendance);
        return ResultUtils.success(true);
    }

    @PostMapping("deleteById")
    public BaseResponse deleteById() {
//        if (attendanceService.deleteById(id) == 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除失败");
//        }
        return ResultUtils.success(true);
    }
}

