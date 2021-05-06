package cn.sqh.web.authentication;

import cn.sqh.domain.result.Result;

import cn.sqh.web.utils.WriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            log.info("用户[{}]于[{}]注销成功!", ((User) authentication.getPrincipal()).getUsername(), new Date());
        }
        WriteResponse.write(httpServletResponse,Result.build(Result.RESULTTYPE_SUCCESS,"注销成功！"));
    }
}
