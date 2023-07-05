package com.example.attendance.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.example.attendance.mapper.LeaveMapper;
import com.example.attendance.service.LeaveBackupService;
import com.example.attendance.service.LeaveCopyService;
import com.example.attendance.service.LeaveService;
import com.example.dto.LeaveDTO;
import com.example.entity.Employee;
import com.example.entity.Leave;
import com.example.enums.ApproveStatusEnum;
import com.example.exception.BusinessException;
import com.example.result.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveBackupService leaveBackupService;

    @Autowired
    private LeaveCopyService leaveCopyService;

    @Override
    public List<Leave> selectByEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return leaveMapper.selectByEmployeeId(employeeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertLeave(Long employeeId, LeaveDTO leaveDTO) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        checkLeaveDTO(leaveDTO);
        Leave leave = new Leave();
        try {
            BeanUtils.copyProperties(leave, leaveDTO);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Employee employee = employeeService.selectById(employeeId);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        Leave leave1 = leaveMapper.selectRecentLeaveByEmployeeId(employeeId);
        if (leave1 != null && leave1.getApproveStatus() == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请假申请未审批");
        }
        leave.setEmployeeId(employeeId);
        leave.setDepartmentId(employee.getDepartmentId());
        leave.setApproveStatus(ApproveStatusEnum.WAITING.getValue());
        leave.setLeaveReason(leaveDTO.getLeaveReason());
        leave.setLeaveType(leaveDTO.getLeaveType());
        leave.setStartDate(leaveDTO.getStartDate());
        leave.setEndDate(leaveDTO.getEndDate());
        leaveMapper.insertLeave(leave);
        for (Long id : leaveDTO.getLeaveBackups()) {
            insertLeaveBackup(leave, id, employee.getDepartmentId());
        }
        for (Long id : leaveDTO.getLeaveCopyReceivers()) {
            Employee employee1 = employeeService.selectById(id);
            if (employee1 == null) {
                throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
            }
            leaveCopyService.insertLeaveCopy(leave.getId(), id);
        }
        return 1;
    }

    @Override
    public int cancelLeave(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Leave leave = leaveMapper.selectRecentLeaveByEmployeeId(employeeId);
        if (leave == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "没有请假记录");
        }
        if (leave.getApproveStatus() != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请假已审批");
        }
        return leaveMapper.deleteById(leave.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateLeave(Long employeeId, LeaveDTO leaveDTO) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (leaveDTO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Leave leave = leaveMapper.selectRecentLeaveByEmployeeId(employeeId);
        if (leave == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "没有请假记录");
        }
        if (leave.getApproveStatus() != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请假已审批");
        }
        if (StringUtils.isNotBlank(leaveDTO.getLeaveReason())) {
            leave.setLeaveReason(leaveDTO.getLeaveReason());
        }
        if (leaveDTO.getLeaveType() != null) {
            leave.setLeaveType(leaveDTO.getLeaveType());
        }
        if (leaveDTO.getStartDate() != null) {
            leave.setStartDate(leaveDTO.getStartDate());
        }
        if (leaveDTO.getEndDate() != null) {
            leave.setEndDate(leaveDTO.getEndDate());
        }
        if (leave.getStartDate().compareTo(leave.getEndDate()) > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "startDate cannot be greater than endDate");
        }
        return leaveMapper.update(leave);
    }

    @Override
    public Leave selectById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return leaveMapper.selectById(id);
    }

    @Override
    public int approveLeave(Long employeeId, Long approverId, Integer status) {
        if (status == null || status <= 0 || status > 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审批状态错误");
        }
        Leave leave = leaveMapper.selectRecentLeaveByEmployeeId(employeeId);
        if (leave == null || leave.getApproveStatus() != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请假申请不存在或已审批");
        }
        Employee employee = employeeService.selectById(employeeId);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        Employee approver = employeeService.selectById(approverId);
        if (approver == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "审批人不存在");
        }
        if (!Objects.equals(employee.getDepartmentId(), approver.getDepartmentId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审批人不在同一部门");
        }
        if (approver.getLevel() != 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审批人不是部门负责人");
        }
        return leaveMapper.approveById(approverId, leave.getId(), status);
    }

    @Override
    public List<Leave> selectByDepartmentId(Long departmentId) {
        if (departmentId == null || departmentId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return leaveMapper.selectByDepartmentId(departmentId);
    }

    private void checkLeaveDTO(LeaveDTO leaveDTO) {
        if (leaveDTO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 如果 leaveDTO 的任意一项为空，就抛出异常
        Objects.requireNonNull(leaveDTO.getStartDate(), "startDate cannot be null");
        Objects.requireNonNull(leaveDTO.getEndDate(), "endDate cannot be null");
        Objects.requireNonNull(leaveDTO.getLeaveType(), "leaveType cannot be null");
        Objects.requireNonNull(leaveDTO.getLeaveReason(), "leaveReason cannot be null");
        // 开始时间不能大于结束时间
        if (leaveDTO.getStartDate().compareTo(leaveDTO.getEndDate()) > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "startDate cannot be greater than endDate");
        }
    }

    private void insertLeaveBackup(Leave leave, Long id, Long departmentId) {
        Employee employee = employeeService.selectById(id);
        if (employee == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "员工不存在");
        }
        if (!Objects.equals(employee.getDepartmentId(), departmentId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "员工不在同一部门");
        }
        leaveBackupService.insertLeaveBackup(leave.getId(), id);
    }
}
