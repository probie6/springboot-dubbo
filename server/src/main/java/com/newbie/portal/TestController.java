package com.newbie.portal;

import com.newbie.basic.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by 王飞 on 2020-01-16
 */
@RequestMapping("/api")
@RestController
@Slf4j
public class TestController {

    @RequestMapping(value = "/hello")
    public ResponseResult hello(String foo) {

        log.info(foo);
        String result = "hello";
        return new ResponseResult<>(result);
    }

}
