
## 架构

### v1.2 (注意 本仓库初始版本叫v1.2)

单体

## 数据存储

### v1.2

MySQL 读写 adminUserToken 对象。


## 使用测试:


1. login 写入 adminUserToken 对象

GET localhost:8081/users/admin/login


2. 读资源 读取 adminUserToken 对象

POST localhost:8081/users/admin/profile, 携带 Header