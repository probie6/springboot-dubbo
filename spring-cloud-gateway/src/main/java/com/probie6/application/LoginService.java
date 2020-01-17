package com.probie6.application;

import com.probie6.application.dto.input.LoginInputDTO;
import com.probie6.application.dto.output.LoginOutputDTO;

/**
 * create by wangfei on 2020-01-16
 */
public interface LoginService {
    LoginOutputDTO login(LoginInputDTO loginInputDTO);
}
