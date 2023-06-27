package com.example.employee.entity;


import java.util.Date;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 考勤表
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 员工 id
     */
    private Long employeeId;

    /**
     * 部门 id
     */
    private Long departmentId;

    /**
     * 考勤日期
     */
    private Date attendanceDate;

    /**
     * 上班打卡时间
     */
    private Date clockInTime;

    /**
     * 下班打卡时间
     */
    private Date clockOutTime;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 更新时间
     */

    private Date updateTime;

    /**
     * 逻辑删除(1:已删除，0:未删除)
     */
    private Integer isDeleted;


}
