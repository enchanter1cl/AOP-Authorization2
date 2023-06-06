package com.erato.userserviceweb.service;

import com.erato.userserviceweb.entity.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (AdminUser)表服务接口
 *
 * @author makejava
 * @since 2023-06-06 02:05:28
 */
public interface AdminUserService {

    public String login(String userName, String password);

    public AdminUser getUserDetailById(Long loginUserId);

    /**
     * 通过ID查询单条数据
     *
     * @param adminUserId 主键
     * @return 实例对象
     */
    AdminUser queryById(Long adminUserId);

    /**
     * 分页查询
     *
     * @param adminUser   筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<AdminUser> queryByPage(AdminUser adminUser, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param adminUser 实例对象
     * @return 实例对象
     */
    AdminUser insert(AdminUser adminUser);

    /**
     * 修改数据
     *
     * @param adminUser 实例对象
     * @return 实例对象
     */
    AdminUser update(AdminUser adminUser);

    /**
     * 通过主键删除数据
     *
     * @param adminUserId 主键
     * @return 是否成功
     */
    boolean deleteById(Long adminUserId);

}
