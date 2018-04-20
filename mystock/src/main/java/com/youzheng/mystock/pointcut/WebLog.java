package com.youzheng.mystock.pointcut;


import com.youzheng.mystock.entity.C_Log;
import com.youzheng.mystock.entity.C_User;
import com.youzheng.mystock.logRepos.LogRepos;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class WebLog {

    @Autowired
    private LogRepos logRepos;

    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(WebLog.class);
    private C_Log log;

    @Pointcut("execution(public * com.youzheng.mystock.inteface.*.save(..))")
    public void WebLog() {

    }

    @Before("WebLog()")
    public void Before(JoinPoint joinPoint) {
        System.out.println("进入doBefore切面");
        log = new C_Log();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        C_User user = (C_User) session.getAttribute("UserInfo");
        log.setUser(user.getId() + "");
        log.setArgs(joinPoint.getArgs()[0].toString());
        log.setCreateTime(new Date().toString());
        log.setOperation(joinPoint.getSignature().getName());
    }

    @After("WebLog()")
    public void After() {
        logRepos.save(log);
    }

}
