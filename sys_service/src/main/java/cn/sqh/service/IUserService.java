package cn.sqh.service;


import cn.sqh.domain.Role;
import cn.sqh.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

    public UserInfo findUserByUsername(String username) throws Exception;

    UserInfo findBasicInfoOfUserById(Integer id) throws Exception;

    UserInfo findBasicInfoOfUserByUsername(String username) throws Exception;

    List<Role> findOtherRoles(Integer userId) throws Exception;

    void addRoleToUser(Integer userId, Integer roleId) throws Exception;

    void addRoleToUser(Integer userId, Integer[] roleId) throws Exception;

    void removeRoleFromUser(Integer userId, Integer[] roleIds) throws Exception;

    void removeRoleFromUser(Integer userId, Integer roleIds) throws Exception;

    void save(UserInfo userInfo, Integer... groupIds) throws Exception;

    void updateUserInfo(Integer userId, UserInfo newUserInfo) throws Exception;

    List<UserInfo> findAllUsersBasicInfo() throws Exception;

    void deleteUserById(Integer userId) throws Exception;
}
