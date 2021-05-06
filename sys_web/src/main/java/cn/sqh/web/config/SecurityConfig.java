package cn.sqh.web.config;

import cn.sqh.service.IUserService;
import cn.sqh.web.authentication.AuthenticationEntryPointHandler;
import cn.sqh.web.authentication.AuthenticationFailureHandlerImpl;
import cn.sqh.web.authentication.AuthenticationSuccessHandlerImpl;
import cn.sqh.web.authentication.LogoutSuccessHandlerImpl;
import cn.sqh.web.filter.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:security-config.properties")
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.login.url}")
    private String loginApi;

    @Value("${security.logout.url}")
    private String logoutApi;

    @Value("${security.ignore.api}")
    private String[] securityIgnoreApi;

    @Value("${security.login.username.key:username}")
    private String usernameKey;

    @Value("${security.login.password.key:password}")
    private String passwordKey;

    @Autowired
    private IUserService userService;

    @Autowired
    private DataSource dataSource; // 数据源

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(CorsConfigurationSource())
//                .cors()
                .and().authorizeRequests()
                .antMatchers(securityIgnoreApi).permitAll()
                .anyRequest().authenticated()
                //这里配置的loginProcessingUrl为页面中对应表单的 action ，该请求为 post，并设置可匿名访问
                .and().formLogin().loginProcessingUrl(loginApi).permitAll()
                //这里指定的是表单中name="username"的参数作为登录用户名，name="password"的参数作为登录密码
                .usernameParameter(usernameKey).passwordParameter(passwordKey)
                //登录成功后的返回结果
                .successHandler(new AuthenticationSuccessHandlerImpl())
                //登录失败后的返回结果
                .failureHandler(new AuthenticationFailureHandlerImpl(usernameKey))
                //这里配置的logoutUrl为登出接口，并设置可匿名访问
                .and().logout().logoutUrl(logoutApi)
                //登出后的返回结果
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl()).deleteCookies("JSESSIONID").permitAll()
                //这里配置的为当未登录访问受保护资源时，返回json
                .and()
                .rememberMe()
                .userDetailsService(userService) // 设置userDetailsService
                .tokenRepository(persistentTokenRepository()) // 设置数据访问层
                .tokenValiditySeconds(60 * 60) // 记住我的时间(秒)
                .and().csrf().csrfTokenRepository(httpSessionCsrfTokenRepository())
                .ignoringAntMatchers(loginApi).ignoringAntMatchers(securityIgnoreApi)
                .and().exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointHandler())
                .and().addFilterAfter(new XssFilter(), CsrfFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    private CorsConfigurationSource CorsConfigurationSource() {
        CorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8081");    //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
//        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("localhost:8081"));
//        corsConfiguration.setAllowedHeaders(Arrays.asList("_csrf","token","X-XSRF-TOKEN","Content-Type","Content-Length","Authorization","X-Requested-With","Origin","Accept"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(60 * 30L);
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST"));//允许的请求方法，PSOT、GET等
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", corsConfiguration); //配置允许跨域访问的url
        return source;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource); // 设置数据源
//        tokenRepository.setCreateTableOnStartup(true); // 启动创建表，创建成功后注释掉
        return tokenRepository;
    }

    @Bean//前后端分离导致不同域名，浏览器同源政策会阻止Cookie保存，前端代码无法获取Cookie值，所以不能使用CookieRepository
    public static CookieCsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository csrfTokenRepository = new CookieCsrfTokenRepository();
//        csrfTokenRepository.setCookieDomain("localhost");
        csrfTokenRepository.setCookieHttpOnly(false);
//        csrfTokenRepository.setCookiePath("/");
        return csrfTokenRepository;
    }

    @Bean
    public static HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("CSRFToken");
        return repository;
    }
}
