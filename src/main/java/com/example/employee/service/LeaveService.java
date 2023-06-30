package com.example.employee.service;

import com.example.employee.entity.Leave;
import com.example.employee.model.dto.LeaveDTO;

import java.util.List;

public interface LeaveService {

    List<Leave> selectByEmployeeId(Long employeeId);

    List<Leave> selectByDepartmentId(Long departmentId);

    int insertLeave(Long employeeId, LeaveDTO leaveDTO);

    int cancelLeave(Long employeeId);

    int updateLeave(Long employeeId, LeaveDTO leaveDTO);

    Leave selectById(Long id);

    int approveLeave(Long approverId, Long employeeId, Integer status);

}
