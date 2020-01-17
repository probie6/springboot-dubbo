package com.probie6.application.impl;

import com.probie6.application.dto.input.LoginInputDTO;
import com.probie6.application.dto.output.LoginOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.probie6.application.LoginService;

/**
 * create by wangfei on 2020-01-16
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Override
    public LoginOutputDTO login(LoginInputDTO loginInputDTO) {
        return new LoginOutputDTO(true);
    }
}
