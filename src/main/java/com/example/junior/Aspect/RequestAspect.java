package com.example.junior.Aspect;

import com.example.junior.Dao.LogDao;
import com.example.junior.Entity.LogEntity;
import com.example.junior.Service.LogService;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect//切面的注解
@Component
public class RequestAspect {

    @Autowired
    private LogService logService;

    //抽取相同代码，定义切点
    @Pointcut("execution(public * com.example.junior.Controller.HelloController.*(..))")
    public void pointDo(){
    }

    @Pointcut("execution(public * com.example.junior.Controller.UserController.*(..))")
    public void userDo(){}

    //@Pointcut("execution(public * com.example.junior.Controller.UserController.(..))")

    //在调用HelloController的所有方法前做的事情
    @Before("pointDo()")
    public void sayBefore()
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        System.out.println("在请求前做的...");
        //url
        System.out.println("url:"+req.getRequestURL());
        //method
        System.out.println("请求的方法:"+req.getMethod());
        //ip
        System.out.println("客户端ip:"+req.getRemoteAddr());
    }

    @After("pointDo()")
    public void sayAfter()
    {
        System.out.println("在请求后做的...");
    }

    @After("userDo()")
    public void logUserBehaviour()
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();

        LogEntity logEntity = new LogEntity();
        logEntity.setIp(req.getRemoteAddr());
        logEntity.setTime(new Date());
        logEntity.setBehaviour(req.getRequestURL().toString());
        logService.logUserBehaviour(logEntity);
    }

    //@Before("")
}
