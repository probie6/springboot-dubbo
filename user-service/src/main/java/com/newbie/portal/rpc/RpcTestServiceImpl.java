package com.newbie.portal.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.newbie.rpc.RpcTestService;

/**
 * create by 王飞 on 2020-01-19
 */
@Service(version = "1.0.0", timeout = 3000)
@org.springframework.stereotype.Service
public class RpcTestServiceImpl implements RpcTestService {
    @Override
    public String hello() {
        return "hello";
    }
}
