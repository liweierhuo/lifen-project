package com.lifen.service;


import com.lifen.dataobject.AdminUsers;

public interface AdminUserService {

    /**
     * 登录
     * @param account
     * @param pwd
     * @return
     */
    AdminUsers login(String account, String pwd);

    /**
     * 注册
     * @param adminUser
     * @return
     */
    AdminUsers userReg(AdminUsers adminUser);
}
