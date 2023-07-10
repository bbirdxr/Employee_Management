package com.example.employee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.example")
@MapperScan(basePackages = "com.example.employee.mapper")
public class ServiceEmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEmployeeApplication.class, args);
    }

}
