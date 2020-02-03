package org.newbie.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * create by wangfei on 2020-02-03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//@Import(UserConfiguration.class) // 基于注解驱动实现Sprig @Enable模块
@Import(UserImportSelector.class) // 基于接口驱动实现Spring @Enable模块
public @interface EnableUserTemplate {
}
