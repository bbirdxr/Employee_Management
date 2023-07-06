package com.example.attendance.service.impl;

import com.example.attendance.mapper.LeaveBackupMapper;
import com.example.attendance.service.LeaveBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveBackupServiceImpl implements LeaveBackupService {

    @Autowired
    private LeaveBackupMapper leaveBackupMapper;

    @Override
    public void insertLeaveBackup(Long leaveId, Long employeeId) {
        leaveBackupMapper.insertLeaveBackup(leaveId, employeeId);
    }

    @Override
    public int deleteById(Long id) {
        return leaveBackupMapper.deleteById(id);
    }
}
