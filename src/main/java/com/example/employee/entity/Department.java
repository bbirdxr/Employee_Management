package com.example.employee.entity;


import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */

    private Long id;

    private String departmentName;

    private Long parentDepartmentId;

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
