package cn.sqh.service.impl;

import cn.sqh.domain.UserInfo;
import cn.sqh.service.IStorageService;
import cn.sqh.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private IStorageService storageService;

    @Test
    public void findUserByUsername() throws Exception {
        UserInfo user = userService.findUserByUsername("tom");
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(user);
        System.out.println(s);

    }

}