package com.example.employee.manager.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.example")
@MapperScan("com.example.employee.manager.mapper")
@EnableFeignClients(basePackages = "com.example.employee.manager.feign")
public class ManagerApplication {

    private static final Logger LOG=LoggerFactory.getLogger(ManagerApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ManagerApplication.class);
        Environment  env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}{}/hello",env.getProperty("server.port"),env.getProperty("server.servlet.context-path"));


    }

}
