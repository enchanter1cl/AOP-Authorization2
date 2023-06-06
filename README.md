# AOP 形式 解决 Token 处理及鉴权

## 概述

一般情况下，后端处理 Token 的步骤总结如下：

第一步是生成 Token。

第二步是获取到前端请求中的 Token 值。

第三步是验证 Token 值，是否存在、是否过期等等。

完成登录功能后，则需要对用户的登录状态进行验证，这里所说的登录状态保持即 “ Token 值是否存在及 Token 值是否有效 ” 。而 Token 值是否有效则通过后端代码实现，由于大部分接口都需要进行登录验证，如果每个方法都添加查询用户数据的语句则有些多余，因此对方法做了抽取，通过注解切面的形式来返回用户信息。

对于管理员用户的身份鉴权，功能实现时定义了一个 `@TokenToAdminUser` 注解。之后自定义了方法参数解析器，在需要用户身份信息的方法定义上添加 `@TokenToAdminUser` 注解，通过方法参数解析器来获得当前登录的对象信息，

`TokenToAdminUser` 源码见 `com.erato.usersvc.config.annotation.TokenToAdminUser`

解析器 `TokenToAdminUserMethodArgumentResolver` 源码见 `com.erato.usersvc.config.handler.TokenToAdminUserMethodArgumentResolver`

执行逻辑如下：

首先获取请求头中的 Token 值，不存在则返回错误信息给前端，存在则继续后续流程。

通过 Token 值来查询 AdminUserToken 对象，是否存在或者是否过期，不存在或者已过期则返回错误信息给前端，正常则继续后续流程。

最后在 `WebMvcConfigurer` 中配置 `TokenToAdminUserMethodArgumentResolver` 使其生效。

`AdminUserWebMvcConfigurer` 源码见 `com.erato.usersvc.config.AdminUserWebConfigurer`

这样，需要进行用户鉴权的API接口定义上添加 `@TokenToAdminUser` 注解即可，之后再进行相应的代码逻辑处理。


## 架构

### v1.2 (注意 本仓库初始版本叫v1.2)

单体

## v1.3

改造为微服务架构，引入网关层。在网关层对请求进行前置的身份验证操作。

服务网关组件是客户端到微服务架构内部的一座桥梁，通过服务网关为请求提供了统一的访问入口，同时也能够对请求做一些定制化的前置处理。比如在当前的场景下，就需要在网关层进行统一鉴权，这样就能够避免无正确身份标识的请求直接进入微服务实例中，如果请求头中有正确的身份标识则放行让后方的微服务实例进行请求处理，如果没有正确的身份标识则直接在网关层响应出一个错误提示即可。

在具体的编码时，就涉及到服务网关的过滤器这个知识点。本项目中，就是使用了 **Spring Cloud Gateway 的全局过滤器**实现鉴权功能。

全局过滤器 `ValidTokenGlobalFilter` 源码见 `com.erato.gatewayadmin.filter.ValidTokenGlobalFilter`

## 数据存储

### v1.2

MySQL 读写 adminUserToken 对象。建库建表 sql 见 `/sql`。

### v1.2.1

Redis 读写 adminUserToken 对象。


## 使用测试:


1. login 写入 adminUserToken 对象

POST localhost:8081/users/admin/login

发送：

```json
{
    "userName": "newbee-admin1",
    "passwordMd5": "e10adc3949ba59abbe56e057f20f883e"
}
```

返回：

```json
{
    "resultCode": 200,
    "message": "SUCCESS",
    "data": "eae09a7924c5e6a2b68e03695f4d9c71" //TOKEN串
}
```


2. 读资源 读取 adminUserToken 对象

POST localhost:8081/users/admin/profile, 携带 Header

Header 为 {“token”: "上面响应得到的 TOKEN 串" }

返回:

```json
{
    "resultCode": 200,
    "message": "SUCCESS",
    "data": {
        "adminUserId": 2,
        "loginUserName": "newbee-admin1",
        "loginPassword": "******",
        "nickName": "用户01",
        "locked": 0
    }
}
```