package com.newbie;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.newbie.config.EnableUserTemplate;
import org.newbie.config.UserTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@EnableDubbo
// 如果使用开启注解，优先通过次注解方式注入bean，如果没开启则通过spring spi机制自动注入
//@EnableUserTemplate
@MapperScan("com.newbie.mapper")
public class UserApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UserApplication.class);
        context.getBean(UserTemplate.class);
    }

    /**
     * 如果不手动注入数据源，springboot会通过自动装配注入，类型为class com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper
     * 手动注入数据源类型为class com.alibaba.druid.pool.DruidDataSource
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}
