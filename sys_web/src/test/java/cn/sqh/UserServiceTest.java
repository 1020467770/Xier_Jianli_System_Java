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

    @Test
    public void test1() throws Exception {
        UserInfo tom = userService.findUserByUsername("tom");
        String s = mapper.writeValueAsString(tom);
        System.out.println(s);

    }

    @Test
    public void test2() throws Exception {
//        sysLogService
//        System.out.println(s);

    }
}
