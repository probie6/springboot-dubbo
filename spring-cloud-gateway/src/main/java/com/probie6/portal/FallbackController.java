package com.probie6.portal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by 王飞 on 2020-01-16
 */
@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public String fallback() {
        return "服务不可用";
    }
}
