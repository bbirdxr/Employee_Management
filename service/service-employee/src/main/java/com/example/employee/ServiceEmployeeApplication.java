<<<<<<< HEAD
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
=======
package com.example.employee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "com.example")
@EnableFeignClients(basePackages = "com.example")
@MapperScan(basePackages = "com.example.employee.mapper")
public class ServiceEmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEmployeeApplication.class, args);
    }

}
>>>>>>> ba0863a92bed74b938c097e7218c38a40a23c806
