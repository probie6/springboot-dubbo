package com.probie6.config.domain.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 江山
 * @date: 2019/12/30 15:46
 * @description:
 */
@ConfigurationProperties("application.swagger")
@Data
@NoArgsConstructor
public class SwaggerProperties {
    private Boolean enabled;
    private Boolean openapiEnabled = true;
    private String title = "";
    private String description = "";
    private String version = "";
    private String license = "";
    private String licenseUrl = "";
    private String termsOfServiceUrl = "";
    private List<Class<?>> ignoredParameterTypes = new ArrayList<>();
    private Contact contact = new Contact();
    private String basePackage = "";
    private List<String> basePath = new ArrayList<>();
    private List<String> excludePath = new ArrayList<>();
    private Map<String,DocketInfo> docket = new LinkedHashMap<>();
    private String host = "";
    private List<GlobalOperationParameter> globalOperationParameters;
    private UiConfig uiConfig = new UiConfig();
    private Boolean applyDefaultResponseMessages = true;
    private GlobalResponseMessage globalResponseMessage;
}
