package com.erato.userserviceweb.config.handler;

import com.erato.cloudcommon.exception.NewBeeMallException;
import com.erato.userserviceweb.config.annotation.TokenToAdminUser;
import com.erato.userserviceweb.entity.AdminUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    RedisTemplate redisTemplate;

    public TokenToAdminUserMethodArgumentResolver() {}

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToAdminUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)  {

        if (parameter.getParameterAnnotation(TokenToAdminUser.class) instanceof TokenToAdminUser) {

            String token = webRequest.getHeader("token");

            if (null != token && !"".equals(token)) {
                ValueOperations<String, AdminUserToken> ops = redisTemplate.opsForValue();
                AdminUserToken adminUserToken = ops.get(token);

                if (adminUserToken == null) {
                    NewBeeMallException.fail("数据库取出 adminUserToken 为null");
                }
                //数据库用 token 为 key 能取到东西， 即可返回
                return adminUserToken;

            } else {
                NewBeeMallException.fail("token为空或空串，说明状态为未登录");
            }
        }
        return null;
    }
}
