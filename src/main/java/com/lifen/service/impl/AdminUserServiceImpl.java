package com.lifen.service.impl;

import com.lifen.dataobject.AdminUsers;
import com.lifen.enums.IsDeletedEnum;
import com.lifen.repository.AdminUserRepository;
import com.lifen.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.beans.Transient;
import java.util.List;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    @Transient
    public AdminUsers login(String account, String pwd) {
        List<AdminUsers> result = adminUserRepository.findByAdminAccount(account);
        if (result == null || result.size() < 0) {
            log.info("{}不存在",account);
            return null;
        } else if (result.size() >= 1) {
            if (StringUtils.equals(result.get(0).getAdminPwd(),pwd)
                    && StringUtils.equals(result.get(0).getIsDeleted(), IsDeletedEnum.NO.getCode())) {
                AdminUsers adminUser = result.get(0);
                adminUser.setLoginNum(adminUser.getLoginNum()+1);
                adminUserRepository.save(adminUser);
                return result.get(0);
            }
        }
        return null;
    }

    @Override
    public AdminUsers userReg(AdminUsers adminUsers) {
        return adminUserRepository.save(adminUsers);
    }
}
