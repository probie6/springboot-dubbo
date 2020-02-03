package com.newbie.outputdto;

import lombok.Data;

import java.io.Serializable;

/**
 * create by wangfei on 2020-02-03
 */
@Data
public class GetUserInfoOutputDTO implements Serializable {
    private String name;

    private Integer age;

    private String profession;

    private String idCard;
}
