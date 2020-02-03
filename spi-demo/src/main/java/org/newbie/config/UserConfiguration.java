package org.newbie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by wangfei on 2020-02-03
 */
@Configuration
public class UserConfiguration {
    @Bean
    public UserTemplate userTemplate() {
        UserTemplate userTemplate = new UserTemplate();
        userTemplate.setName("zhangsan");
        return userTemplate;
    }

}
