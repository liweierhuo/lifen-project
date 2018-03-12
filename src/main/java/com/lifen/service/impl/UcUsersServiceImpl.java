package com.lifen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lifen.dataobject.UcUsers;
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
import org.thymeleaf.util.StringUtils;

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
    public UcUsers login(String account, String pwd,String userType) {
        List<UcUsers> users = ucUsersRepository.findAllByUserAccountAndUserType(account, userType);
        if (users != null && users.size() >= 1) {
            if (StringUtils.equals(users.get(0).getUserPwd(),pwd)) {
                return users.get(0);
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

    @Override
    public UcUsers update(UcUsers ucUsers) throws Exception{
        if (ucUsers.getUserId() == null ) {
            throw new Exception("用户userId为空，不能做更新操作");
        }
        return ucUsersRepository.save(ucUsers);
    }

    @Override
    public UcUsers findByUserAccount(String userAccount) {
        return ucUsersRepository.findAllByUserAccount(userAccount);
    }

    @Override
    public Page<UcUsers> getUserList(PageRequest pageRequest, UcUsers ucUser) {
        Pageable pageable = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.ASC, "userId");
        Specification<UcUsers> userSpec = new Specification<UcUsers>() {
            @Override
            public Predicate toPredicate(Root<UcUsers> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null != ucUser.getUserType() &&! "".equals(ucUser.getUserType())){
                    list.add(criteriaBuilder.equal(root.get("userType").as(String.class), ucUser.getUserType()));
                }
                if(null!=ucUser.getUserName() &&!"".equals(ucUser.getUserName())){
                    list.add(criteriaBuilder.like(root.get("userName").as(String.class), ucUser.getUserName()));
                }
                if(null!=ucUser.getUserAccount()&&!"".equals(ucUser.getUserAccount())){
                    list.add(criteriaBuilder.equal(root.get("userAccount").as(String.class), ucUser.getUserAccount()));
                }

                if(null!=ucUser.getIsDeleted()&&!"".equals(ucUser.getIsDeleted())){
                    list.add(criteriaBuilder.equal(root.get("isDeleted").as(String.class), ucUser.getIsDeleted()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        return ucUsersRepository.findAll(userSpec, pageable);
    }
}
