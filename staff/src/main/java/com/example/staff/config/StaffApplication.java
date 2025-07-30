package com.example.staff.config;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.example")
public class StaffApplication {

    private static final Logger LOG=LoggerFactory.getLogger(StaffApplication.class);
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StaffApplication.class);
        Environment  env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}{}/hello",env.getProperty("server.port"),env.getProperty("server.servlet.context-path"));


    }

}
