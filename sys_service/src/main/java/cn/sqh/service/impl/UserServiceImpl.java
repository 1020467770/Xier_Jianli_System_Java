package cn.sqh.service.impl;

import cn.sqh.dao.IMessageDao;
import cn.sqh.dao.IUserDao;
import cn.sqh.domain.Role;
import cn.sqh.domain.UserInfo;
import cn.sqh.service.IMessageService;
import cn.sqh.service.IUserService;
import cn.sqh.utils.BCryptPasswordEncoderUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IMessageService messageService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = userDao.findByUsername(username);
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String userJson = mapper.writeValueAsString(userInfo);
            System.out.println(userJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //处理自己的用户对象封装成UserDetails
        /*这里的User对象是spring-security提供的*/
        /*{noop}前缀是在明文情况下绕过spring-security的办法，否则会报错*/
//        User user = new User(userInfo.getUsername(), "{noop}" + userInfo.getPassword(), getAuthority(userInfo.getRoleList()));
        User user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getActiveStatus() == 1
                , true, true, true, getAuthority(userInfo.getRoleList()));
        return user;
    }

    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
//        list.add(new SimpleGrantedAuthority("ROLE_SADMIN"));
        return list;
    }

    @Override
    public UserInfo findUserByUsername(String username) throws Exception {
        return userDao.findByUsername(username);
    }


    @Override
    public UserInfo findBasicInfoOfUserById(Integer id) throws Exception {
        return userDao.findBasicInfoOfUserById(id);
    }


    @Override
    public UserInfo findBasicInfoOfUserByUsername(String username) {
        try {
            return userDao.findBasicInfoOfUserByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Role> findOtherRoles(Integer userId) throws Exception {
        return userDao.findOtherRoles(userId);
    }

    @Override
    public void addRoleToUser(Integer userId, Integer[] roleIds) throws Exception {
        for (Integer roleId : roleIds) {
            userDao.addRoleToUser(userId, roleId);
        }
    }

    @Override
    public void removeRoleFromUser(Integer userId, Integer[] roleIds) throws Exception {
        for (Integer roleId : roleIds) {
            userDao.removeRoleFromUser(userId, roleId);
        }

    }

    @Override
    public void removeRoleFromUser(Integer userId, Integer roleId) throws Exception {
        userDao.removeRoleFromUser(userId, roleId);
    }

    @Override
    public void save(UserInfo userInfo, Integer... groupIds) throws Exception {
        String password = userInfo.getPassword();
        if (password != null) {
            String encodePassword = BCryptPasswordEncoderUtils.encodePassword(password);
            userInfo.setPassword(encodePassword);
        }
        userInfo.setUpdateDate(new Date());
        userDao.save(userInfo);
        final Integer userId = userInfo.getId();
        final String username = userInfo.getUsername();
        for (Integer groupId : groupIds) {
            userDao.addGroupToUser(userId, username, groupId);
            messageService.notifyNewMessageToGroup("有一位新的成员加入了你的小组", groupId, false);
        }
    }

    @Override
    public void updateUserInfo(Integer userId, UserInfo newUserInfo) throws Exception {
        userDao.updateUserInfo(userId, newUserInfo.getName(), newUserInfo.getQQ(), newUserInfo.getPhoneNum());

    }

    @Override
    public List<UserInfo> findAllUsersBasicInfo() throws Exception {
        return userDao.findAllUsersBasicInfo();
    }

    @Override
    public void deleteUserById(Integer userId) throws Exception {
        userDao.deleteByPrimaryKey(userId);
    }

    @Override
    public void addRoleToUser(Integer userId, Integer roleId) throws Exception {
        userDao.addRoleToUser(userId, roleId);
    }

}
