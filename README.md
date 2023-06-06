
## 架构

### v1.2 (注意 本仓库初始版本叫v1.2)

单体

## 数据存储

### v1.2

MySQL 读写 adminUserToken 对象。建库建表 sql 见 `/sql`


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