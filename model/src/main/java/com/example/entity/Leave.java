package com.example.entity;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 请假表
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Leave implements Serializable {

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
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 请假类型
     */
    private Integer leaveType;

    /**
     * 请假原因
     */
    private String leaveReason;

    /**
     * 审批人 id
     */
    private Long approverId;

    /**
     * 请假状态
     */
    private Integer approveStatus;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 更新时间
     */

    private Date updateTime;

    private Integer isDeleted;

}
