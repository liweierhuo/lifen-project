package com.lifen.repository;

import com.lifen.dataobject.UcUsers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


/**
 * Created by liwei on 2018/3/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UcUsersRepositoryTest {

    @Autowired
    private UcUsersRepository ucUsersRepository;

    @Test
    public void save() {
        UcUsers ucUsers = new UcUsers();
        ucUsers.setGender("F");
        ucUsers.setIsDeleted("N");
        ucUsers.setUserPwd("admin");
        ucUsers.setUserAccount("admin");
        ucUsers.setUserName("李峰");
        ucUsers.setCreateTime(new Date());
        ucUsers.setEmail("213412341234@qq.com");
        ucUsers.setMobile("1123412341234");
        ucUsers.setLoginNum(1);
        ucUsers.setUserCode("123412341234");
        UcUsers result = ucUsersRepository.save(ucUsers);
        Assert.assertNotNull(result);
    }


}