package com.newbie;

import com.alibaba.druid.pool.DruidDataSource;
import com.newbie.basic.ResponseResult;
import com.newbie.plugin.HelloPlugin;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan("com.newbie.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * 如果不手动注入数据源，springboot会通过自动装配注入，类型为class com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper
     * 手动注入数据源类型为class com.alibaba.druid.pool.DruidDataSource
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}
