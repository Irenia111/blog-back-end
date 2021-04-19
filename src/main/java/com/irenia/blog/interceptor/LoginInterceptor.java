package com.irenia.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截访问admin的请求，只在session中包含user的情况才能访问admin-blog
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //如果session中不包含user，那么重定向到login页面
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;//终止执行
        }
        return true;//继续执行
    }
}
