package cn.sqh.web.authentication;

import cn.sqh.domain.result.Result;
import cn.sqh.web.utils.WriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private String usernameKey;

    public AuthenticationFailureHandlerImpl(String usernameKey) {
        this.usernameKey = usernameKey;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String username = httpServletRequest.getParameter(usernameKey);
        log.info("用户[{}]于[{}]登录失败", username, new Date());
        WriteResponse.write(httpServletResponse, Result.build(Result.RESULTTYPE_FAILED, "登录失败！"));
    }
}
