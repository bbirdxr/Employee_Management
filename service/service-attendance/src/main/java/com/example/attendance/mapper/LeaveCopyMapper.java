package com.example.attendance.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LeaveCopyMapper {
    /**
     * 插入请假抄送
     *
     * @param leaveId 请假 id
     * @param employeeId 抄送人 id
     */
    int insertLeaveBackup(@Param("leaveId") Long leaveId, @Param("employeeId") Long employeeId);

    /**
     * 删除请假抄送
     *
     */
    int deleteById(Long id);
}
