package com.lifen.controller.admin;

import com.lifen.constant.ProjectConstant;
import com.lifen.dataobject.AdminUsers;
import com.lifen.service.AdminUserService;
import com.lifen.utils.ResultMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liwei on 2018/3/8.
 */
@Controller
@Slf4j
public class UserController {
    @Autowired
    private AdminUserService adminUserService;
    @GetMapping("admin/login")
    public String loginInit() {
        log.info("进入管理员登录页面");
        return "views/admin/login";
    }

    @ResponseBody
    @PostMapping("admin/do_login")
    public ResultMap doLogin(String account, String password,HttpServletRequest request) {
        AdminUsers loginUser = adminUserService.login(account, password);
        if (loginUser == null) {
            return ResultMap.error("登录失败");
        }
        request.getSession().setAttribute(ProjectConstant.ADMIN_USER_SESSION_KEY,loginUser);
        return ResultMap.ok("登录成功").put("user", loginUser);
    }

    @GetMapping("admin/index")
    public String indexInit() {
        log.info("进入管理员主页面");
        return "views/admin/index";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value= "admin/login_out", method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request) {
        log.info("退出系统");
        request.getSession().removeAttribute(ProjectConstant.ADMIN_USER_SESSION_KEY);
        return "views/admin/login";
    }

    @GetMapping(value= "admin/{path}")
    public String loginOut(@PathVariable("path") String path,HttpServletRequest request,Model model) {
        log.info("访问页面"+path);
        model.addAttribute("appServer","http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath());
        return "views/admin/"+path;
    }
}
