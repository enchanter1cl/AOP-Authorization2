package com.erato.userserviceweb.service.impl;

import com.erato.cloudcommon.pojo.AdminUserToken;
import com.erato.cloudcommon.util.SystemUtil;
import com.erato.userserviceweb.dao.AdminUserMapper;
import com.erato.userserviceweb.entity.AdminUser;
import com.erato.userserviceweb.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * (AdminUser)表服务实现类
 *
 * @author makejava
 * @since 2023-06-06 02:05:28
 */
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    RedisTemplate redisTemplate;
    @Resource
    private AdminUserMapper adminUserMapper;

    public String login(String userName, String password) {

        // 读库 读出 adminUser
        AdminUser loginAdminUser = adminUserMapper.login(userName, password);
        if (loginAdminUser != null) {
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", loginAdminUser.getAdminUserId());

            //不需要判断库里存不存在该 adminUserToken（mysql 无则新增 有则更新）, 也不需要插入 updateTime, expireTime 字段
            AdminUserToken adminUserToken = new AdminUserToken();
            adminUserToken.setAdminUserId(loginAdminUser.getAdminUserId());
            adminUserToken.setToken(token);

            // 写入redis库
            ValueOperations<String, AdminUserToken> ops = redisTemplate.opsForValue();
            ops.set(token, adminUserToken, 48, TimeUnit.HOURS);

            //新增成功后返回
            return token;
        }
        return "登录失败";
    }

    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + 123456;
        return SystemUtil.genToken(src);
    }

    @Override
    public AdminUser getUserDetailById(Long loginUserId) {
        return adminUserMapper.queryById(loginUserId);
    }













    /**
     * 通过ID查询单条数据
     *
     * @param adminUserId 主键
     * @return 实例对象
     */
    @Override
    public AdminUser queryById(Long adminUserId) {
        return this.adminUserMapper.queryById(adminUserId);
    }

    /**
     * 分页查询
     *
     * @param adminUser   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<AdminUser> queryByPage(AdminUser adminUser, PageRequest pageRequest) {
        long total = this.adminUserMapper.count(adminUser);
        return new PageImpl<>(this.adminUserMapper.queryAllByLimit(adminUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param adminUser 实例对象
     * @return 实例对象
     */
    @Override
    public AdminUser insert(AdminUser adminUser) {
        this.adminUserMapper.insert(adminUser);
        return adminUser;
    }

    /**
     * 修改数据
     *
     * @param adminUser 实例对象
     * @return 实例对象
     */
    @Override
    public AdminUser update(AdminUser adminUser) {
        this.adminUserMapper.update(adminUser);
        return this.queryById(adminUser.getAdminUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param adminUserId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long adminUserId) {
        return this.adminUserMapper.deleteById(adminUserId) > 0;
    }
}
