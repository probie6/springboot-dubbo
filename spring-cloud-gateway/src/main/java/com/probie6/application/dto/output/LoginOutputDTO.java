package com.probie6.application.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * create by wangfei on 2020-01-16
 */
@Data
@AllArgsConstructor
public class LoginOutputDTO implements Serializable {
    @ApiModelProperty(value = "登录验证是否通过")
    private Boolean checkResult;
}
