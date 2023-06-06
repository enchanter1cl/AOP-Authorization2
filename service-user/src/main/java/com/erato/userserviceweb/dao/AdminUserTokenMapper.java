package com.erato.userserviceweb.dao;

import com.erato.userserviceweb.entity.AdminUserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (AdminUserToken)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-06 01:48:13
 */

@Mapper
public interface AdminUserTokenMapper {

    AdminUserToken selectByToken(String token);

    /**
     * 通过ID查询单条数据
     *
     * @param adminUserId 主键
     * @return 实例对象
     */
    AdminUserToken queryById(Long adminUserId);

    /**
     * 查询指定行数据
     *
     * @param adminUserToken 查询条件
     * @param pageable       分页对象
     * @return 对象列表
     */
    List<AdminUserToken> queryAllByLimit(AdminUserToken adminUserToken, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param adminUserToken 查询条件
     * @return 总行数
     */
    long count(AdminUserToken adminUserToken);

    /**
     * 新增数据
     *
     * @param adminUserToken 实例对象
     * @return 影响行数
     */
    int insert(AdminUserToken adminUserToken);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AdminUserToken> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AdminUserToken> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AdminUserToken> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AdminUserToken> entities);

    /**
     * 修改数据
     *
     * @param adminUserToken 实例对象
     * @return 影响行数
     */
    int update(AdminUserToken adminUserToken);

    /**
     * 通过主键删除数据
     *
     * @param adminUserId 主键
     * @return 影响行数
     */
    int deleteById(Long adminUserId);

}

