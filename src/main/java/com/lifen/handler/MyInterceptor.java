package com.lifen.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by liwei on 2018/3/14.
 */
public class MyInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        logger.info("------preHandle------");
        String excludePaths = "/login.html,/user_reg.html,/uc/do_login";
        //获取访问路径 ，并且把sevlet路径中的admin文件给替换掉
        String path=request.getServletPath();
        //如果该路径中不存在.jsp，那么就不执行过滤操作
        if(path.indexOf(".html") == -1 || path.indexOf(".json") == -1) {
            return true;
        } else {
            path.substring(0,path.indexOf("."));
            //如果该页面的路经是.jsp后缀的那么要判断他是不是被排除不用过滤的文件
            if (path.contains("admin")) {
                return true;
            }
            if(excludePaths.contains(path)){
                return true;
            }
        }
        //获取session
        HttpSession session = request.getSession(true);
        //判断用户ID是否存在，不存在就跳转到登录界面
        if (session.getAttribute("ucUser") == null) {
            logger.info("------:跳转到login页面！");
            response.sendRedirect(request.getContextPath() + "/login.html");
            return false;
        } else {
            session.setAttribute("ucUser", session.getAttribute("ucUser"));
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }


}
