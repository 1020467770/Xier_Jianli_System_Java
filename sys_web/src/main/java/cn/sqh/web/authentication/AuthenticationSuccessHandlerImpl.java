package cn.sqh.web.authentication;

import cn.sqh.domain.result.LoginResult;
import cn.sqh.domain.result.Result;
import cn.sqh.web.utils.WriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

//    @Autowired
//    private CookieCsrfTokenRepository cookieCsrfTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("成功登录拦截器...");
        //登录成功后获取当前登录用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("用户[{}]于[{}]登录成功!", user.getUsername(), new Date());
        try {
            LoginResult loginResult = new LoginResult();
            loginResult.setUser(user);
            DefaultCsrfToken csrfToken = (DefaultCsrfToken) httpServletRequest.getSession().getAttribute("CSRFToken");
            loginResult.setCsrfToken(csrfToken.getToken());
//            UserInfo userInfo = userService.findUserByUsername(user.getUsername());
            WriteResponse.write(httpServletResponse, Result.build(Result.RESULTTYPE_SUCCESS, loginResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*CookieCsrfTokenRepository.withHttpOnlyFalse()
        final CsrfToken csrfToken = cookieCsrfTokenRepository.loadToken(httpServletRequest);
        System.out.println(csrfToken.getToken());*/

    }
}
