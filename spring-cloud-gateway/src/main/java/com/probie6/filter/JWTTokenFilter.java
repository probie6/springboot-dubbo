package com.probie6.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newbie.basic.ResponseResult;
import com.newbie.basic.enums.ResponseTypes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.probie6.utils.JWTUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * create by wangfei on 2020-01-16
 */
@Slf4j
@Component
@ConfigurationProperties("org.pass.jwt")
public class JWTTokenFilter implements GlobalFilter, Ordered {

    private String[] skipUrls = {"/login"};
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        log.info("初始化JWTTokenFilter过滤器");
    }

    @Autowired
    public JWTTokenFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        if(url != null && Arrays.asList(skipUrls).contains(url)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        ServerHttpResponse resp = exchange.getResponse();
        if(StringUtils.isEmpty(token)) {
            return authErro(resp, "请登录");
        }

        try {
            JWTUtils.validateJWT(token);
            return chain.filter(exchange);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            return authErro(resp, e.getMessage());
        }
    }

    /**
     * 认证错误输出
     * @param resp 响应对象
     * @param mess 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp,String mess) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        ResponseResult<String> returnData = new ResponseResult<>(ResponseTypes.TOKEN_UNVALID, mess, mess);
        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(returnData);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(),e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
