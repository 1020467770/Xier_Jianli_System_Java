package cn.sqh.web.aop;

import cn.sqh.domain.result.Result;
import cn.sqh.web.interfaces.LimitRequest;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class LimitRequestAop {

    //url - addr - count
    private static ConcurrentHashMap<String, ExpiringMap<String, Integer>> baseMap = new ConcurrentHashMap<>();

    // 定义切点
    // 让所有有@LimitRequest注解的方法都执行切面方法
    @Pointcut("@annotation(limitRequest)")
    public void excudeService(LimitRequest limitRequest) {
    }

    @Around("excudeService(limitRequest)")
    public Object doAround(ProceedingJoinPoint pjp, LimitRequest limitRequest) throws Throwable {

        //RequestContextHolder法获取request
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();

        ExpiringMap<String, Integer> tempMap = baseMap.getOrDefault(uri, ExpiringMap.builder().variableExpiration().build());
        Integer count = tempMap.getOrDefault(ip, 0);//访问次数
//        System.out.println("当前IP" + ip + "访问" + uri + "次数：" + count);

        if (count >= limitRequest.count()) {
            log.warn("ip[{}]于[{}]访问[{}]达到限制次数[{}]!", ip, new Date(), uri, count);
            return Result.build(Result.RESULTTYPE_DINIED, "IP访问次数达到限制");
//            throw new Exception();//抛出异常
        } else if (count == 0) {//第一次请求
            tempMap.put(ip, count + 1, ExpirationPolicy.CREATED, limitRequest.time(), TimeUnit.MILLISECONDS);
        } else {
            tempMap.put(ip, count + 1);
        }
        baseMap.put(uri, tempMap);

        Object result = pjp.proceed();
        return result;
    }


}
