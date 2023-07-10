package com.example.attendance.service.impl;

import com.example.attendance.mapper.LeaveCopyMapper;
import com.example.attendance.service.LeaveCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveCopyServiceImpl implements LeaveCopyService {

    @Autowired
    private LeaveCopyMapper leaveCopyMapper;

    @Override
    public int insertLeaveCopy(Long leaveId, Long employeeId) {
        return leaveCopyMapper.insertLeaveBackup(leaveId, employeeId);
    }

    @Override
    public int deleteById(Long id) {
        return leaveCopyMapper.deleteById(id);
    }
}
