package com.lifen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}
