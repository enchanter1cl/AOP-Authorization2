package com.erato.cloudcommon.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * (AdminUserToken)实体类
 *
 * @author makejava
 * @since 2023-06-06 01:48:14
 */

@Data
@ToString
public class AdminUserToken implements Serializable {
    private static final long serialVersionUID = 151175558133254292L;
    /**
     * 用户主键id
     */
    private Long adminUserId;
    /**
     * token值(32位字符串)
     */
    private String token;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * token过期时间
     */
    private Date expireTime;


    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

}

