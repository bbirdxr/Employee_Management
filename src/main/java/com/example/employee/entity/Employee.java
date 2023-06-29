package com.example.employee.entity;

import java.math.BigDecimal;

import java.util.Date;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;

/**
 * <p>
 * 员工信息
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

//    private Long departmentId;
//
//    private Long positionId;

    private String name;

    private String email;

    private String phoneNumber;

    private Date hireDate;

    private BigDecimal salary;

    private Integer level;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
