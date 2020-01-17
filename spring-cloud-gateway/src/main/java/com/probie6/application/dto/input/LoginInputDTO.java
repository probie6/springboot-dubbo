package com.probie6.application.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * create by wangfei on 2020-01-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInputDTO implements Serializable {
    private String userName;
    private String password;
}
