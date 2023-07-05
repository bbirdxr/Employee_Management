package com.example.attendance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.example")
@MapperScan(basePackages = "com.example.attendance.mapper")
public class ServiceAttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAttendanceApplication.class, args);
    }

}