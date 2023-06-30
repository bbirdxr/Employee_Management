package com.example.employee.mapper;

import com.example.employee.entity.Leave;
import com.example.employee.model.dto.LeaveDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 请假表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Mapper
public interface LeaveMapper {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 请假表
     */
    Leave selectById(Long id);

    /**
     * 新增数据
     *
     * @param leave 实例对象
     * @return 影响行数
     */
    int insertLeave(Leave leave);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 修改数据
     * @param leave 请假更新
     * @return 影响行数
     */
    int update(Leave leave);

    /**
     * 负责人审批
     * @param approverId 负责人 ID
     * @param id 请假 ID
     * @param status 状态
     * @return
     */
    int approveById(@Param("approverId") Long approverId, @Param("id") Long id, @Param("approveStatus") Integer approveStatus);

    /**
     * 根据员工 ID 查询最近的一条请假记录
     * @param employeeId 员工 ID
     * @return 请假记录
     */
    Leave selectRecentLeaveByEmployeeId(Long employeeId);

    List<Leave> selectByEmployeeId(Long employeeId);

    List<Leave> selectByDepartmentId(Long departmentId);
}
