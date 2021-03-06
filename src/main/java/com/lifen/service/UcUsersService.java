package com.lifen.service;

import com.lifen.dataobject.UcUsers;
import com.lifen.dto.UcUserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UcUsersService {

    /**
     * 登录
     * @param account
     * @param pwd
     * @return
     */
    UcUsers login(String account, String pwd,String userType);

    /**
     * 注册
     * @param ucUsers
     * @return
     */
    UcUsers userReg(UcUsers ucUsers);

    void delete(Long userId);

    boolean logicDelete(Long userId);
    UcUsers update(UcUsers ucUsers) throws Exception;
    UcUsers findByUserAccountOrUserId(String userAccount,Long userId);

    Page<UcUsers> getUserList(PageRequest pageRequest,UcUserQuery ucUserQuery);
}
