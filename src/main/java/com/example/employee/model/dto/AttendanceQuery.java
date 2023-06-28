package com.example.employee.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceQuery {
    private Long employeeId;

    private Long departmentId;

    private Date attendanceDate;
}
