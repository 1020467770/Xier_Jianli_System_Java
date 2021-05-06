package cn.sqh.web.aop;


import cn.sqh.domain.SysLog;
import cn.sqh.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired//注入法获取request
    private HttpServletRequest request;//这个报错是假的，在web里声明了监听器，会自动注入到spring容器中

    @Autowired
    private ISysLogService sysLogService;

    private Date visitTime;//访问开始的时间
    private Class clazz;//访问的类
    private Method method;//访问的方法


    //前置通知 主要获取开始时间，执行的类是哪一个，执行的是哪一个方法
    @Before("execution(* cn.sqh.web.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date();//当前时间就是开始访问的时间
        clazz = jp.getTarget().getClass();//具体要访问的类
        String methodName = jp.getSignature().getName();//获取访问的方法的名称
        Object[] args = jp.getArgs();//获取访问的方法的参数


        //获取具体执行的方法的method对象
        try {
            if (args == null || args.length == 0) {
                this.method = clazz.getMethod(methodName);//只能获取无参的方法
            } else {
                Class[] classArgs = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    classArgs[i] = args[i].getClass();
                }
                this.method = clazz.getMethod(methodName, classArgs);
            }
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        } catch (SecurityException e) {
//            e.printStackTrace();
        }
    }


    //后置通知
    @After("execution(* cn.sqh.web.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        long time = System.currentTimeMillis() - visitTime.getTime();//获取访问的时长

        String url = "";
        //获取url
        if (clazz != null && method != null && clazz != LogAop.class) {
            //1.获取类上的@RequestMapping("/user")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String classUrl = (classAnnotation.value())[0];

                //2.获取方法上的注解的@RequestMapping
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String methodUrl = (methodAnnotation.value())[0];
                    url = classUrl + methodUrl;

                    //获取访问的ip地址
                    String ip = request.getRemoteAddr();

                    //获取当前操作的用户
                    //可以通过spring security提供的SecurityContext获取登录的用户对象
                    User user = null;//会返回一个user对象
                    String username = null;
                    SysLog sysLog = new SysLog();
                    try {
                        SecurityContext context = SecurityContextHolder.getContext();//从上下文中获取当前登录的用户
                        user = (User) context.getAuthentication().getPrincipal();
                        username = user.getUsername();
                        sysLog.setAuthority(user.getAuthorities().toString());
                    } catch (Exception e) {
                        username = "Anonymous";
                        sysLog.setAuthority("ROLE_ANONYMOUS");
                    }

                    //将日志相关信息封装到SysLog对象
                    sysLog.setExecutionTime(time);
                    sysLog.setIp(ip);
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);
                    sysLog.setMethod("[类名] " + clazz.getName() + "[方法名] " + method.getName());

                    //调用service完成数据库insert操作

                    sysLogService.save(sysLog);

                }
            }
        }

    }

}

