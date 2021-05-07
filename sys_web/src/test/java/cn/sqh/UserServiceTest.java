package cn.sqh;

import cn.sqh.domain.UserInfo;
import cn.sqh.service.ISysLogService;
import cn.sqh.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysLogService sysLogService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public void test1() throws Exception {
        UserInfo tom = userService.findUserByUsername("tom");
        String s = mapper.writeValueAsString(tom);
        System.out.println(s);

    }


    @Test
    public void test3() throws Exception {
        System.out.println("用户名为：" + mailSender.getUsername());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("encountersqh@qq.com");
        simpleMailMessage.setTo("1020467770@qq.com");
        simpleMailMessage.setSubject("BugBugBug");
        simpleMailMessage.setText("一杯茶，一根烟，一个Bug改一天");
        mailSender.send(simpleMailMessage);
    }
}
