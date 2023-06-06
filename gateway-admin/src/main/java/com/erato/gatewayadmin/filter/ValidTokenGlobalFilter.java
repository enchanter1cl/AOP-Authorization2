package com.erato.gatewayadmin.filter;

import com.erato.cloudcommon.dto.Result;
import com.erato.cloudcommon.dto.ResultGenerator;
import com.erato.cloudcommon.pojo.AdminUserToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class ValidTokenGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 请求头不为空则取出其中名称为token请求头的值，如果该值不存在则直接返回错误提示，不会将请求转发到后方的微服务实例中。
     * 根据token值查询Redis数据库中是否存在对应的Token，如果Redis数据库中不存在对应的数据则直接返回错误提示，不会将请求转发到后方的微服务实例中。
     * 如果Redis数据库中存在对应的数据则表示鉴权成功，将请求转发到后方的微服务实例中去处理请求。
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        /* 登录接口，直接放行 */
        if (exchange.getRequest().getURI().getPath().equals("/users/admin/login")) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();

        if (headers == null || headers.isEmpty()) {
            /* 返回错误提示 */
            log.info("header 无 token 字段");
            return wrapErrorResponse(exchange, chain);
        }

        String token = headers.getFirst("token");

        if (StringUtils.isEmpty(token)) {
            /* 返回错误提示 */
            log.info("header 有 token 但是是空串");
            return wrapErrorResponse(exchange, chain);
        }

        /* 从 redis 读 token */
        ValueOperations<String, AdminUserToken> ops = redisTemplate.opsForValue();
        AdminUserToken tokenObject = ops.get(token);
        if (tokenObject == null) {
            /* 返回错误提示 */
            log.info("redis 无此 token");
            return wrapErrorResponse(exchange, chain);
        }

        return chain.filter(exchange);
    }

    /**
     * 设优先级
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 包装返回response
     * @param exchange
     * @param chain
     * @return
     */
    Mono<Void> wrapErrorResponse(ServerWebExchange exchange, GatewayFilterChain chain) {

        Result result = ResultGenerator.genErrorResult(419, "无权限访问");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(result);
        byte[] bytes = jsonNode.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return exchange.getResponse().writeWith(Flux.just(dataBuffer));
    }
}
