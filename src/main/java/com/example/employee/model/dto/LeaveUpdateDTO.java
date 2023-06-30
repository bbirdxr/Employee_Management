package com.example.employee.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LeaveUpdateDTO {

    private Date startDate;

    private Date endDate;

    private Integer leaveType;

    private String leaveReason;
}
