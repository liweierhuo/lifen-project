package com.lifen.controller;

import com.lifen.constant.ProjectConstant;
import com.lifen.dataobject.UcUsers;
import com.lifen.enums.IsDeletedEnum;
import com.lifen.enums.UserTypeEnum;
import com.lifen.service.UcUsersService;
import com.lifen.utils.PageResult;
import com.lifen.utils.ResultMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by 廖师兄
 * 2017-06-18 23:27
 */
@RestController
@Slf4j
public class UcUsersController {

    @Autowired
    private UcUsersService ucUsersService;

    @GetMapping("admin/get_teacherList")
    public PageResult<UcUsers> getTeacherList(Integer page, Integer limit,Model model, UcUsers ucUsers){
        PageRequest pageRequest = new PageRequest(page - 1,limit);
        ucUsers.setUserType(UserTypeEnum.TEACHER.getCode());
        ucUsers.setIsDeleted(IsDeletedEnum.NO.getCode());
        Page<UcUsers> result = ucUsersService.getUserList(pageRequest,ucUsers);
        PageResult<UcUsers> userList = new PageResult<UcUsers>(result.getTotalElements(),result.getContent());
        return userList;
    }

    @GetMapping("admin/get_studentList")
    public PageResult<UcUsers> getStudentList(Integer page, Integer limit,Model model, UcUsers ucUsers){
        PageRequest pageRequest = new PageRequest(page - 1,limit);
        ucUsers.setUserType(UserTypeEnum.STUDENT.getCode());
        ucUsers.setIsDeleted(IsDeletedEnum.NO.getCode());
        Page<UcUsers> result = ucUsersService.getUserList(pageRequest,ucUsers);
        PageResult<UcUsers> userList = new PageResult<UcUsers>(result.getTotalElements(),result.getContent());
        return userList;
    }

    @PostMapping("uc/add_user")
    public ResultMap addUser(UcUsers ucUsers){
        ucUsers.setCreateTime(new Date());
        ucUsers.setUpdateTime(new Date());
        ucUsers.setLoginNum(0);
        ucUsers.setUserCode(ucUsers.getUserAccount());
        ucUsers.setIsDeleted(IsDeletedEnum.NO.getCode());
        UcUsers userResult = ucUsersService.findByUserAccountOrUserId(ucUsers.getUserAccount(),ucUsers.getUserId());
        if (userResult != null) {
            return ResultMap.error("用户账号不能重复");
        }
        UcUsers ucUser = ucUsersService.userReg(ucUsers);
        if (ucUser != null) {
            return ResultMap.ok("操作成功，跳转到登录页面");
        } else {
            return ResultMap.error();
        }
    }

    @PostMapping("/uc/do_login")
    public ResultMap doLogin(String account, String password,String userType,HttpServletRequest request){
        UcUsers ucUser = ucUsersService.login(account,password,userType);
        if (ucUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute(ProjectConstant.UC_USER_SESSION_KEY,ucUser);
            return ResultMap.ok("操作成功").put("user", ucUser);
        } else {
            return ResultMap.error();
        }
    }

    @ResponseBody
    @GetMapping("/uc/getUserByUserAccountOrUserId")
    public ResultMap getUserByUserAccountOrUserId(String account,Long userId ,HttpServletRequest request){
        UcUsers ucUser = ucUsersService.findByUserAccountOrUserId(account,userId);
        if (ucUser != null) {
            return ResultMap.ok("操作成功").put("user", ucUser);
        } else {
            return ResultMap.error();
        }
    }

}
