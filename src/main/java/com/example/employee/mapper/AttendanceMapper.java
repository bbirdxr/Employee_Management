package com.example.employee.mapper;

import com.example.employee.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 考勤表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Mapper
public interface AttendanceMapper {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 考勤表
     */
    Attendance selectById(@Param("id") Long id);
}