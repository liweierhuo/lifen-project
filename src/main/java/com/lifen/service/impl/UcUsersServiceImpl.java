package com.lifen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lifen.dataobject.UcUsers;
import com.lifen.dto.UcUserQuery;
import com.lifen.enums.IsDeletedEnum;
import com.lifen.repository.UcUsersRepository;
import com.lifen.service.UcUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Transient;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class UcUsersServiceImpl implements UcUsersService {

    @Autowired
    private UcUsersRepository ucUsersRepository;

    @Override
    @Transient
    public UcUsers login(String account, String pwd,String userType)  {
        List<UcUsers> users = ucUsersRepository.findAllByUserAccountAndUserType(account, userType);
        if (users != null && users.size() >= 1) {
            if (StringUtils.equals(users.get(0).getUserPwd(),pwd)) {
                UcUsers ucUser = users.get(0);
                ucUser.setLoginNum(ucUser.getLoginNum() == null ? 0:ucUser.getLoginNum()+1);
                try {
                    ucUser = this.update(ucUser);
                } catch (Exception e) {
                    return null;
                }
                return ucUser;
            }
        }
        return null;
    }

    @Override
    public UcUsers userReg(UcUsers ucUsers) {
        return ucUsersRepository.save(ucUsers);
    }

    @Override
    public void delete(Long userId) {
        ucUsersRepository.delete(userId);
    }

    @Transient
    @Override
    public boolean logicDelete(Long userId) {
        UcUsers ucUser = ucUsersRepository.findOne(userId);
        ucUser.setIsDeleted(IsDeletedEnum.YES.getCode());
        ucUsersRepository.save(ucUser);
        return true;
    }

    @Override
    public UcUsers update(UcUsers ucUsers) throws Exception{
        if (ucUsers.getUserId() == null ) {
            throw new Exception("用户userId为空，不能做更新操作");
        }
        return ucUsersRepository.save(ucUsers);
    }

    @Override
    public UcUsers findByUserAccountOrUserId(String userAccount,Long userId) {
        if (StringUtils.isEmpty(userAccount) && userId == null) {
            log.error("userAccount and userId need have one");
            return null;
        }
        if (!StringUtils.isEmpty(userAccount)) {
            return ucUsersRepository.findAllByUserAccount(userAccount);
        } else {
            return ucUsersRepository.findOne(userId);
        }
    }

    @Override
    public Page<UcUsers> getUserList(PageRequest pageRequest, UcUserQuery ucUserQuery) {
        Pageable pageable = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.DESC, "userId");
        Specification<UcUsers> userSpec = new Specification<UcUsers>() {
            @Override
            public Predicate toPredicate(Root<UcUsers> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null != ucUserQuery.getUserType() &&! "".equals(ucUserQuery.getUserType())){
                    list.add(criteriaBuilder.equal(root.get("userType").as(String.class), ucUserQuery.getUserType()));
                }
                if(null!=ucUserQuery.getUserName() &&!"".equals(ucUserQuery.getUserName())){
                    list.add(criteriaBuilder.like(root.get("userName").as(String.class), ucUserQuery.getUserName()));
                }
                if(null!=ucUserQuery.getUserAccount()&&!"".equals(ucUserQuery.getUserAccount())){
                    list.add(criteriaBuilder.equal(root.get("userAccount").as(String.class), ucUserQuery.getUserAccount()));
                }
                if(null!=ucUserQuery.getCreateStartTime() && null != ucUserQuery.getCreateEndTime()){
                    list.add(criteriaBuilder.between(root.get("createTime").as(Date.class),
                            ucUserQuery.getCreateStartTime(),ucUserQuery.getCreateEndTime()));
                }

                if(null!=ucUserQuery.getIsDeleted()&&!"".equals(ucUserQuery.getIsDeleted())){
                    list.add(criteriaBuilder.equal(root.get("isDeleted").as(String.class), ucUserQuery.getIsDeleted()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        return ucUsersRepository.findAll(userSpec, pageable);
    }
}
