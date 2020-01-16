package com.probie6;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * create by 王飞 on 2020-01-16
 */
@SpringBootApplication
@Slf4j
public class GatewayApplication {
    public static void main(String[] args) {
        log.info("启动gateway网关服务...");
        SpringApplication.run(com.probie6.GatewayApplication.class, args);
    }
}
