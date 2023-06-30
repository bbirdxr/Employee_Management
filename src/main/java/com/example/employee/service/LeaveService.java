package com.example.employee.service;

import com.example.employee.entity.Leave;
import com.example.employee.model.dto.LeaveUpdateDTO;

import java.util.List;

public interface LeaveService {

    List<Leave> selectByEmployeeId(Long employeeId);

    List<Leave> selectByDepartmentId(Long departmentId);

    int insert(Leave leave);

    int cancelLeave(Long employeeId);

    int updateLeave(Long employeeId, LeaveUpdateDTO leaveUpdateDTO);

    Leave selectById(Long id);

    int approveByEmployeeId(Long approverId, Long employeeId, Integer status);

}
