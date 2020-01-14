package com.newbie.common.util;

import com.newbie.dto.OutputDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 张元平
 * @Description 属性模型输出参数
 * @Date 2019/9/23
 */
@Data
@ApiModel(description = "属性模型输出参数")
public class AttrModelOutputDTO implements OutputDTO {

    @ApiModelProperty(name = "key", value = "属性名称")
    private String key;

    @ApiModelProperty(name = "display", value = "属性说明")
    private String display;

    @ApiModelProperty(name = "sourceProp", value = "源头属性名")
    private String sourceProp;

}

