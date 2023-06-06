package com.erato.userserviceweb.dao;

import com.erato.userserviceweb.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (AdminUser)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-06 02:05:28
 */

@Mapper
public interface AdminUserMapper {

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @return
     */
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过ID查询单条数据
     *
     * @param adminUserId 主键
     * @return 实例对象
     */
    AdminUser queryById(Long adminUserId);

    /**
     * 查询指定行数据
     *
     * @param adminUser 查询条件
     * @param pageable  分页对象
     * @return 对象列表
     */
    List<AdminUser> queryAllByLimit(AdminUser adminUser, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param adminUser 查询条件
     * @return 总行数
     */
    long count(AdminUser adminUser);

    /**
     * 新增数据
     *
     * @param adminUser 实例对象
     * @return 影响行数
     */
    int insert(AdminUser adminUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AdminUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AdminUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AdminUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AdminUser> entities);

    /**
     * 修改数据
     *
     * @param adminUser 实例对象
     * @return 影响行数
     */
    int update(AdminUser adminUser);

    /**
     * 通过主键删除数据
     *
     * @param adminUserId 主键
     * @return 影响行数
     */
    int deleteById(Long adminUserId);

}

