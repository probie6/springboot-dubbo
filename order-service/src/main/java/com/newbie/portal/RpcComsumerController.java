package com.newbie.portal;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.newbie.basic.ResponseResult;
import com.newbie.basic.enums.ResponseTypes;
import com.newbie.inputdto.GetUserInfoInputDTO;
import com.newbie.outputdto.GetUserInfoOutputDTO;
import com.newbie.rpc.RpcUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by 王飞 on 2020-01-19
 */
@RequestMapping("/api")
@RestController
@Slf4j
public class RpcComsumerController {

    @Reference(version = "1.0.0", timeout = 6000)
    private RpcUserService rpcUserService;

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping(value = "/getUserInfo")
    public ResponseResult hello(String id) {
        GetUserInfoInputDTO infoInputDTO = new GetUserInfoInputDTO();
        infoInputDTO.setId(id);
        GetUserInfoOutputDTO result = rpcUserService.getUserInfo(infoInputDTO);
        return new ResponseResult<>(ResponseTypes.SUCCESS, "", result);
    }

    public ResponseResult error(String id) {
        return new ResponseResult<>(ResponseTypes.FAIL, "服务出错,params:{" + id + "}");
    }
}
