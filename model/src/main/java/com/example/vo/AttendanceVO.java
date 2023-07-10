package com.example.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AttendanceVO {
    @ExcelProperty(value = "上班打卡时间" ,index = 0)
    private Date clockInTime;

    @ExcelProperty(value = "下班打卡时间" ,index = 1)
    private Date clockOutTime;
}
