package com.probie6.config;

import com.probie6.config.domain.swagger.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;


@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v2/api-docs";
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    @Bean
    @ConditionalOnMissingBean
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    public UiConfiguration uiConfiguration(SwaggerProperties swaggerProperties) {
        return UiConfigurationBuilder
                .builder()
                .deepLinking(swaggerProperties.getUiConfig().getDeepLinking())
                .defaultModelExpandDepth(swaggerProperties.getUiConfig().getDefaultModelExpandDepth())
                .defaultModelRendering(swaggerProperties.getUiConfig().getDefaultModelRendering())
                .defaultModelsExpandDepth(swaggerProperties.getUiConfig().getDefaultModelsExpandDepth())
                .displayOperationId(swaggerProperties.getUiConfig().getDisplayOperationId())
                .supportedSubmitMethods(swaggerProperties.getUiConfig().getSubmitMethods().split(","))
                .displayRequestDuration(swaggerProperties.getUiConfig().getDisplayRequestDuration())
                .docExpansion(swaggerProperties.getUiConfig().getDocExpansion())
                .maxDisplayedTags(swaggerProperties.getUiConfig().getMaxDisplayedTags())
                .operationsSorter(swaggerProperties.getUiConfig().getOperationsSorter())
                .showExtensions(swaggerProperties.getUiConfig().getShowExtensions())
                .tagsSorter(swaggerProperties.getUiConfig().getTagsSorter())
                .validatorUrl(swaggerProperties.getUiConfig().getValidatorUrl())
                .build();
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
            .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                        predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI).concat("")))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

    public SwaggerProvider(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
    }

}