package com.example.staff.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example")
public class StaffApplication {
    public static void main(String[] args) {

        SpringApplication.run(StaffApplication.class, args);

    }

}
