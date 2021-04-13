package com.irenia.blog.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControlExceptionHandler {
    // 获得日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception exception) {
        // 记录错误日志
        logger.error("Request URL : {}, Exception : {}", request.getRequestURI(), exception);

        // 设置返回的ModelAndView
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("exception", exception);
        // 设置返回的页面
        mv.setViewName("error/error");
        return mv;
    }
}
