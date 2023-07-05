package com.example.attendance.service;

public interface LeaveCopyService {
    int insertLeaveCopy(Long leaveId, Long employeeId);

    int deleteById(Long id);
}
