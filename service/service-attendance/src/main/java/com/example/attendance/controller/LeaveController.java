package com.example.attendance.controller;

import com.example.attendance.service.LeaveService;
import com.example.dto.LeaveDTO;
import com.example.entity.Leave;
import com.example.exception.BusinessException;
import com.example.result.BaseResponse;
import com.example.result.ErrorCode;
import com.example.result.ResultUtils;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/attendance")
@Slf4j
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/employee/{employeeId}")
    public BaseResponse<List<Leave>> selectByEmployeeId(@PathVariable Long employeeId) {
        return ResultUtils.success(leaveService.selectByEmployeeId(employeeId));
    }

    @GetMapping("/department/{departmentId}")
    public BaseResponse<List<Leave>> selectByDepartmentId(@PathVariable Long departmentId) {
        return ResultUtils.success(leaveService.selectByDepartmentId(departmentId));
    }

    @PutMapping("/{employeeId}")
    public BaseResponse updateLeave(@PathVariable Long employeeId, @RequestBody LeaveDTO leaveDTO) {
        return ResultUtils.success(leaveService.updateLeave(employeeId, leaveDTO));
    }

    @PostMapping("/{employeeId}")
    public BaseResponse insertLeave(@PathVariable Long employeeId, @RequestBody LeaveDTO leaveDTO) {
        leaveService.insertLeave(employeeId, leaveDTO);
        return ResultUtils.success(true);
    }

    @PutMapping("cancel/{employeeId}")
    public BaseResponse cancelLeave(@PathVariable Long employeeId) {
        if (leaveService.cancelLeave(employeeId) == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "取消失败");
        }
        return ResultUtils.success(true);
    }

    @PutMapping("approve/{employeeId}/{approverId}/{status}")
    public BaseResponse approveLeave(@PathVariable Long employeeId, @PathVariable Long approverId, @PathVariable Integer status) {
        if (leaveService.approveLeave(employeeId, approverId, status) == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审批失败");
        }
        return ResultUtils.success(true);
    }
}

