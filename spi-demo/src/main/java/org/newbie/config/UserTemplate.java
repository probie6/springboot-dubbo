package org.newbie.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * create by wangfei on 2020-02-03
 */
@Data
@Slf4j
public class UserTemplate {
    private String name;

    public UserTemplate() {
        log.info("load UserTemplate");
    }
    public String getUserName() {
        return name;
    }
}
