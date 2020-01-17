package com.probie6.config.domain.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 江山
 * @date: 2019/12/30 15:53
 * @description:
 */
@Data
@NoArgsConstructor
class DocketInfo {
    private String title = "";
    private String description = "";
    private String version = "";
    private String license = "";
    private String licenseUrl = "";
    private String termsOfServiceUrl = "";
    private Contact contact = new Contact();
    private String basePackage = "";
    private List<String> basePath = new ArrayList<>();
    private List<String> excludePath = new ArrayList<>();
    private List<GlobalOperationParameter> globalOperationParameters;
    private List<Class<?>> ignoredParameterTypes = new ArrayList<>();

}
