package com.fushanconsumer.common.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//登录过滤器
//@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.printf("进入 LoginFilter 过滤器。。。");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null){
            //非法访问，没有登陆，跳转到登陆页面
            session.setAttribute("error", "非法访问");
            // 保存客户想要去的地址, 登录成功后则直接跳转,而不是到首页
            String goURL = request.getServletPath();//(获取到地址不包括参数)
            //判断参数是否为空，不null就获取参数
            if(request.getQueryString() != null){
                goURL+="?"+request.getQueryString();
            }
            session.setAttribute("goURL", goURL);
            request.getRequestDispatcher("/login").forward(request,response);
//            response.sendRedirect(request.getContextPath() + "/login");
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
