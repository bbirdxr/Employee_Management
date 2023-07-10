package com.example.attendance.service;

public interface LeaveBackupService {
    void insertLeaveBackup(Long leaveId, Long employeeId);

    int deleteById(Long id);
}
