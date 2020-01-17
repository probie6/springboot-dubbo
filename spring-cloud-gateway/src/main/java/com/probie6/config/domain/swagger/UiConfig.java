package com.probie6.config.domain.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;

/**
 * @author: 江山
 * @date: 2019/12/30 15:54
 * @description:
 */
@Data
@NoArgsConstructor
public class UiConfig {
    private Boolean jsonEditor = false;
    private Boolean showRequestHeaders = true;
    private String submitMethods;
    private Boolean displayTryItOut = false;
    private Long requestTimeout = 10000L;
    private Boolean deepLinking;
    private Boolean displayOperationId;
    private Integer defaultModelsExpandDepth;
    private Integer defaultModelExpandDepth;
    private ModelRendering defaultModelRendering;
    private Boolean displayRequestDuration = true;
    private DocExpansion docExpansion;
    private Object filter;
    private Integer maxDisplayedTags;
    private OperationsSorter operationsSorter;
    private Boolean showExtensions;
    private TagsSorter tagsSorter;
    private String validatorUrl;

    public String getSubmitMethods() {
        return Boolean.TRUE.equals(this.displayTryItOut) ? "get,post,put,delete,patch" : "";
    }
}
