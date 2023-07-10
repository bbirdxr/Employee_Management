package com.example.attendance.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LeaveBackupMapper {
    /**
     * 插入请假交接
     *
     * @param leaveId 请假 id
     * @param employeeId 员工 id
     */
    int insertLeaveBackup(@Param("leaveId") Long leaveId, @Param("employeeId") Long employeeId);

    /**
     * 删除请假交接
     */
    int deleteById(Long id);

}
