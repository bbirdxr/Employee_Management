package com.example.attendance.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "localhost:8082", name = "service-attendance")
public class AttendanceFeignClient {

}
