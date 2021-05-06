package cn.sqh.web.authentication;

import cn.sqh.domain.result.Result;
import cn.sqh.web.utils.WriteResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("全拦截器。。。");
        WriteResponse.write(httpServletResponse, Result.build(Result.RESULTTYPE_DINIED, "权限不足，拒绝访问！"));
    }
}
