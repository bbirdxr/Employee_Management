package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceQuery {
    private Long employeeId;

    private Long departmentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate attendanceDate;
}
