package com.irenia.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // AOP 作用与web包下的全部方法
    @Pointcut("execution(* com.irenia.blog.web.*.*(..))")
    public void log() {
    }

    // 在log 切面执行前执行
    // 记录：请求url 用户ip 方法名classMethod 方法调用参数args
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("------------doBefore-----------");
        // 获得request url = request.getRequestURI(); ip = request.getRemoteAddr();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获得classMethod, 通过切面对象获取
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()
                + "."
                + joinPoint.getSignature().getName();

        // 获得参数
        ReqeustLog reqeustLog = new ReqeustLog(
                request.getRequestURL().toString(),
                request.getRemoteAddr(),
                classMethod,
                joinPoint.getArgs());

        logger.info("Request  ----- {}", reqeustLog);
    }

    @After("log()")
    public void doAfter() {
        logger.info("------------doAfter-----------");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("------------doAfterReturn-----------");
        logger.info(String.format("return result : {}%s", result));
    }

    private class ReqeustLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public ReqeustLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "ReqeustLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

    }
}
