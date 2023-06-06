package com.erato.userserviceweb.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * (AdminUser)实体类
 *
 * @author makejava
 * @since 2023-06-06 02:05:28
 */

@Data
@ToString
public class AdminUser implements Serializable {
    private static final long serialVersionUID = -86157243003736265L;
    /**
     * 管理员id
     */
    private Long adminUserId;
    /**
     * 管理员登陆名称
     */
    private String loginUserName;
    /**
     * 管理员登陆密码
     */
    private String loginPassword;
    /**
     * 管理员显示昵称
     */
    private String nickName;
    /**
     * 是否锁定 0未锁定 1已锁定无法登陆
     */
    private Integer locked;


    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

}

