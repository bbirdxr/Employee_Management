package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ErrorCode;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Leave;
import com.example.employee.exception.BusinessException;
import com.example.employee.model.dto.LeaveDTO;
import com.example.employee.service.LeaveService;
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

    @GetMapping("selectByEmployeeId/{employeeId}")
    public BaseResponse<List<Leave>> selectByEmployeeId(@PathVariable Long employeeId) {
        return ResultUtils.success(leaveService.selectByEmployeeId(employeeId));
    }

    @GetMapping("selectByDepartmentId/{departmentId}")
    public BaseResponse<List<Leave>> selectByDepartmentId(@PathVariable Long departmentId) {
        return ResultUtils.success(leaveService.selectByDepartmentId(departmentId));
    }

    @PutMapping("updateLeave/{employeeId}")
    public BaseResponse updateLeave(@PathVariable Long employeeId, @RequestBody LeaveDTO leaveDTO) {
        return ResultUtils.success(leaveService.updateLeave(employeeId, leaveDTO));
    }

    @PostMapping("insertLeave/{employeeId}")
    public BaseResponse insertLeave(@PathVariable Long employeeId, @RequestBody LeaveDTO leaveDTO) {
        leaveService.insertLeave(employeeId, leaveDTO);
        return ResultUtils.success(true);
    }

    @PostMapping("cancelLeave/{employeeId}")
    public BaseResponse cancelLeave(@PathVariable Long employeeId) {
        if (leaveService.cancelLeave(employeeId) == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "取消失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("approveLeave/{employeeId}/{approverId}/{status}")
    public BaseResponse approveLeave(@PathVariable Long employeeId, @PathVariable Long approverId, @PathVariable Integer status) {
        if (leaveService.approveLeave(employeeId, approverId, status) == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审批失败");
        }
        return ResultUtils.success(true);
    }
}

