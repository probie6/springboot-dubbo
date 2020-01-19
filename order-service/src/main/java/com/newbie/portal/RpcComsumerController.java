package com.newbie.portal;

import com.alibaba.dubbo.config.annotation.Reference;
import com.newbie.basic.ResponseResult;
import com.newbie.basic.enums.ResponseTypes;
import com.newbie.rpc.RpcTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by 王飞 on 2020-01-19
 */
@RequestMapping("/api")
@RestController
@Slf4j
public class RpcComsumerController {

    @Reference(version = "1.0.0", timeout = 3000)
    private RpcTestService rpcTestService;

    @RequestMapping(value = "/hello")
    public ResponseResult hello(String foo) {
        String result = rpcTestService.hello();
        return new ResponseResult<>(ResponseTypes.SUCCESS, "", result);
    }
}
