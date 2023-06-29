package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ErrorCode;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Leave;
import com.example.employee.exception.BusinessException;
import com.example.employee.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 请假表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("selectByEmployeeId/{employeeId}")
    public BaseResponse<List<Leave>> selectByEmployeeId(@PathVariable Long employeeId) {
        return ResultUtils.success(leaveService.selectByEmployeeId(employeeId));
    }

    @GetMapping("selectByDepartmentId/{departmentId}")
    public BaseResponse<List<Leave>> selectByDepartmentId(@PathVariable Long departmentId) {
        return ResultUtils.success(leaveService.selectByDepartmentId(departmentId));
    }

    @PostMapping("insert")
    public BaseResponse insert(@RequestBody Leave leave) {
        leaveService.insert(leave);
        return ResultUtils.success(true);
    }

    @PostMapping("deleteLeave/{employeeId}")
    public BaseResponse deleteLeave(@PathVariable Long employeeId) {
        if (leaveService.deleteLeave(employeeId) == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }

}

