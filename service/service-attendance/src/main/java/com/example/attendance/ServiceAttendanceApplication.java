package com.example.attendance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.example")
@MapperScan(basePackages = "com.example.attendance.mapper")
@EnableFeignClients(basePackages = "com.example")
@EnableRetry
public class ServiceAttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAttendanceApplication.class, args);
    }

}
