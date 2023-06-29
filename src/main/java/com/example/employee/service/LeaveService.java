package com.example.employee.service;

import com.example.employee.entity.Leave;
import com.example.employee.model.dto.LeaveUpdateDTO;

import java.util.List;

public interface LeaveService {

    List<Leave> selectByEmployeeId(Long employeeId);

    int insert(Leave leave);

    int deleteLeave(Long employeeId);

    int updateLeave(LeaveUpdateDTO leaveUpdateDTO);

    Leave selectById(Long id);

    int approveById(Long approverId, Long employeeId, Integer status);

    List<Leave> selectByDepartmentId(Long departmentId);
}
