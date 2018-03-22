package com.lifen.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liwei on 2018/3/15.
 */
@Component
@WebFilter(urlPatterns = "/*",filterName = "loginFilter")
public class LoginFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    String nofilter="";
    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse,
                         FilterChain filter) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletrequest;
        HttpServletResponse response=(HttpServletResponse)servletresponse;
        //获取访问路径 ，并且把sevlet路径中的admin文件给替换掉
        String path=request.getServletPath();
        request.getServletContext().setAttribute("appServer","http://"+request.getLocalAddr()+":"+request.getLocalPort()
                +request.getContextPath());
        //如果该路径中不存在.jsp，那么就不执行过滤操作
        if(path.indexOf(".html") == -1 && path.indexOf(".json") == -1) {
            filter.doFilter(servletrequest,servletresponse );
        } else {
            path.substring(0,path.indexOf("."));
            //如果该页面的路经是.jsp后缀的那么要判断他是不是被排除不用过滤的文件
            if(nofilter.contains(path)){
                filter.doFilter(servletrequest,servletresponse );
            } else if (path.contains("admin")) {
                //如果该文件是应该过滤的文件则需要判断该用户是不是登录用户，如果是登录用户就让他访问否则返回至登录页面
                if(request.getSession().getAttribute("adminUser") != null){
                    request.getSession().setAttribute("adminUser", request.getSession().getAttribute("adminUser"));
                    filter.doFilter(servletrequest,servletresponse );
                } else{
                    logger.info("------:跳转到login页面！");
                    response.sendRedirect(request.getContextPath() + "/admin/login.html");
                }
            } else {
                //如果该文件是应该过滤的文件则需要判断该用户是不是登录用户，如果是登录用户就让他访问否则返回至登录页面
                if(request.getSession().getAttribute("ucUser") != null){
                    request.getSession().setAttribute("ucUser", request.getSession().getAttribute("ucUser"));
                    filter.doFilter(servletrequest,servletresponse );
                } else{
                    logger.info("------:跳转到login页面！");
                    response.sendRedirect(request.getContextPath() + "/login.html");
                }
            }
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        nofilter = "/login.html,/user_reg.html,/uc/do_login.json,/uc/add_user.json,/admin/login.html,/admin/do_login.json";//取得不过滤的页面
    }
}
