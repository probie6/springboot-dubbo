package com.newbie.basic.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * create by wangfei on 2020-01-14
 */
@Data
public class BaseEntity {
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后修改时间")
    private Date lastModifyTime;

    @ApiModelProperty(value = "是否删除，Y/N")
    private String del;
}
