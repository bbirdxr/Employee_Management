package com.example.employee.service.impl;

import com.example.employee.common.ErrorCode;
import com.example.employee.entity.Leave;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.LeaveMapper;
import com.example.employee.model.dto.LeaveUpdateDTO;
import com.example.employee.service.DepartmentService;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public List<Leave> selectByEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (employeeService.selectById(employeeId) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return leaveMapper.selectByEmployeeId(employeeId);
    }

    @Override
    public int insert(Leave leave) {
        return 0;
    }

    @Override
    public int deleteLeave(Long employeeId) {
        Leave leave = leaveMapper.selectRecentLeaveByEmployeeId(employeeId);
        if (leave == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return leaveMapper.deleteById(leave.getId());
    }

    @Override
    public int updateLeave(LeaveUpdateDTO leaveUpdateDTO) {
        if (leaveUpdateDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Leave leave = leaveMapper.selectById(leaveUpdateDTO.getId());
        if (leave == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return 0;
    }

    @Override
    public Leave selectById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return leaveMapper.selectById(id);
    }

    @Override
    public int approveById(Long approverId, Long employeeId, Integer status) {
        Leave leave = leaveMapper.selectRecentLeaveByEmployeeId(employeeId);
        if (leave == null || leave.getApproveStatus() != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return leaveMapper.approveById(approverId, leave.getId(), status);
    }

    @Override
    public List<Leave> selectByDepartmentId(Long departmentId) {
        if (departmentId == null || departmentId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (departmentService.selectById(departmentId) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return leaveMapper.selectByDepartmentId(departmentId);
    }
}
