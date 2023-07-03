package com.example.employee.mapper;

import com.example.employee.entity.Attendance;
import com.example.employee.model.dto.AttendanceQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


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
    // @InterceptAnnotation
    Attendance selectById(@Param("id") Long id);

    /**
     * 新增数据
     *
     * @param attendance 实例对象
     * @return 影响行数
     */
    int insert(Attendance attendance);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 修改数据
     *
     * @param attendance 实例对象
     * @return 影响行数
     */
    int updateById(Attendance attendance);

    /**
     * 根据 AttendanceQuery 查询
     *
     * @param attendanceQuery 查询条件
     * @return 考勤表的集合
     */
    List<Attendance> selectByAttendanceQuery(AttendanceQuery attendanceQuery);
}