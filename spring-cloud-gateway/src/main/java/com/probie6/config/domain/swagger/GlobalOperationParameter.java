package com.probie6.config.domain.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 江山
 * @date: 2019/12/30 15:52
 * @description:
 */
@Data
@NoArgsConstructor
public class GlobalOperationParameter {
    private String name;
    private String description;
    private String modelRef;
    private String parameterType;
    private String required;
}
