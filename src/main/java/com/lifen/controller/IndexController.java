package com.lifen.controller;

import com.lifen.constant.ProjectConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class IndexController {

    @GetMapping("login")
    public String login(Model model){
        return "views/login";
    }

    @GetMapping("user_reg")
    public String initUserReg(Model model){
        return "views/user_reg";
    }


    @GetMapping("login_out")
    public String loginOut(Model model,HttpServletRequest request){
        request.getSession().removeAttribute(ProjectConstant.UC_USER_SESSION_KEY);
        return "views/login";
    }

    @GetMapping("index")
    public String indexInit(Model model,HttpServletRequest request){
        return "views/index";
    }
}
