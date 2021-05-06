package cn.sqh.web.interfaces;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitRequest {
    long time() default 1000*60*5;//限制时间：5分钟
    int count() default 10;//限制次数 10次
}
