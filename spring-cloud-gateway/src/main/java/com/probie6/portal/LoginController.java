package com.probie6.portal;

import com.newbie.basic.ResponseResult;
import com.newbie.basic.enums.ResponseTypes;
import com.probie6.application.dto.input.LoginInputDTO;
import com.probie6.application.dto.output.LoginOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.probie6.application.LoginService;
import com.probie6.utils.JWTUtils;

import java.util.UUID;

/**
 * create by wangfei on 2020-01-16
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public ResponseResult login(String name, String pwd) {
        LoginInputDTO loginInputDTO = new LoginInputDTO(name, pwd);
        LoginOutputDTO loginOutputDTO = loginService.login(loginInputDTO);
        if(Boolean.TRUE.equals(loginOutputDTO.getCheckResult())) {
            String token = JWTUtils.createJWT(String.valueOf(UUID.randomUUID()), name, "test", 20 * 1000L);
            return new ResponseResult<>(ResponseTypes.SUCCESS, "登录成功", token);
        }
        return new ResponseResult<>(ResponseTypes.USER_PASSWORD_ERROR);
    }

}
