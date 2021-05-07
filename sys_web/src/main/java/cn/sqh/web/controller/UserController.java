package cn.sqh.web.controller;

import cn.sqh.domain.Message;
import cn.sqh.domain.result.Result;
import cn.sqh.domain.Role;
import cn.sqh.domain.UserInfo;
import cn.sqh.service.IMessageService;
import cn.sqh.service.IUserService;
import cn.sqh.utils.MD5;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@ResponseBody
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IMessageService messageService;

    //后端不规定传来的Userinfo需要哪些，但一定要完成用户自己修改个人信息的功能这个接口才算满足需求
    @RequestMapping(value = "/addNewUser_2.do", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @RolesAllowed({"SADMIN"})
    public Result addNewUser_2(@RequestParam("user") UserInfo userInfo,
                               @RequestParam("groupIds") Integer groupIds
    ) throws Exception {
        UserInfo userFound = userService.findBasicInfoOfUserByUsername(userInfo.getUsername());
        if (userFound == null) {
            userService.save(userInfo, groupIds);
            return Result.build(Result.RESULTTYPE_SUCCESS, null);
        }
        return Result.build(Result.RESULTTYPE_FAILED, null);
    }

    @RequestMapping(value = "/addNewUser.do", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @RolesAllowed({"SADMIN"})
    public Result addNewUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("major") String major,
                             @RequestParam("email") String email,
                             @RequestParam("QQ") String QQ,
                             @RequestParam("phoneNum") String phoneNum,
                             @RequestParam("groupIds") Integer[] groupIds
    ) throws Exception {
        UserInfo userFound = userService.findBasicInfoOfUserByUsername(username);
        if (userFound == null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setName(name);
            userInfo.setMajor(major);
            userInfo.setEmail(email);
            userInfo.setQQ(QQ);
            userInfo.setPhoneNum(phoneNum);
            userInfo.setActiveStatus(1);
            userInfo.setCode(MD5.MD5Encode(UUID.randomUUID().toString(), "utf-8"));
            userService.save(userInfo, groupIds);
            return Result.build(Result.RESULTTYPE_SUCCESS, null);
        }
        return Result.build(Result.RESULTTYPE_FAILED, null);
    }

    @RequestMapping(value = "/getAllPersonalInfo.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"SADMIN"})
    public Result getAllPersonalInfo(@RequestParam("username") String username) throws Exception {
        UserInfo userInfo = userService.findUserByUsername(username);
        return Result.build(Result.RESULTTYPE_SUCCESS, userInfo);
    }

    @RequestMapping(value = "/getBasicPersonalInfo.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"SADMIN"})
    public Result getBasicPersonalInfo(@RequestParam("username") String username) throws Exception {
        UserInfo userInfo = userService.findBasicInfoOfUserByUsername(username);
        return Result.build(Result.RESULTTYPE_SUCCESS, userInfo);
    }

    @RequestMapping(value = "/findBasicInfoOfUserById.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"SADMIN"})
    public Result findBasicInfoOfUserById(@RequestParam("userId") Integer id) throws Exception {
        UserInfo userInfo = userService.findBasicInfoOfUserById(id);
        return Result.build(Result.RESULTTYPE_SUCCESS, userInfo);
    }

    @RequestMapping(value = "/findOtherRolesByUserId.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"SADMIN"})
    public Result findOtherRolesByUserId(@RequestParam("userId") Integer userId) throws Exception {
        List<Role> otherRoles = userService.findOtherRoles(userId);
        return Result.build(Result.RESULTTYPE_SUCCESS, otherRoles);
    }

    //给用户添加角色
    @RequestMapping(value = "/addRolesToUser.do", method = RequestMethod.POST)
    @RolesAllowed({"SADMIN"})
    public Result addRolesToUser(@RequestParam(name = "userId", required = true) Integer userId,
                                 @RequestParam(name = "roleIds", required = true) Integer[] roleIds) throws Exception {
        userService.addRoleToUser(userId, roleIds);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/addOneRoleToUser.do", method = RequestMethod.POST)
    @RolesAllowed({"SADMIN"})
    public Result addOneRoleToUser(@RequestParam(name = "userId", required = true) Integer userId,
                                   @RequestParam(name = "roleId", required = true) Integer roleId) throws Exception {
        userService.addRoleToUser(userId, roleId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/removeRolesFromUser.do", method = RequestMethod.POST)
    @RolesAllowed({"SADMIN"})
    public Result removeRolesFromUser(@RequestParam(name = "userId", required = true) Integer userId,
                                      @RequestParam(name = "roleIds", required = true) Integer[] roleIds) throws Exception {
        userService.removeRoleFromUser(userId, roleIds);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/removeOneRoleFromUser.do", method = RequestMethod.POST)
    @RolesAllowed({"SADMIN"})
    public Result removeOneRoleFromUser(@RequestParam(name = "userId", required = true) Integer userId,
                                        @RequestParam(name = "roleId", required = true) Integer roleId) throws Exception {
        userService.removeRoleFromUser(userId, roleId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/findAllUsersBasicInfo.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"SADMIN"})
    public Result findAllUsersBasicInfo() throws Exception {
        List<UserInfo> userInfoList = userService.findAllUsersBasicInfo();
        return Result.build(Result.RESULTTYPE_SUCCESS, userInfoList);
    }

    @PostMapping(value = "/deleteUserById.do")
    @RolesAllowed({"SADMIN"})
    public Result deleteUserById(@RequestParam(name = "userId", required = true) Integer userId) throws Exception {
        userService.deleteUserById(userId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

//    =============================================================================

    @RequestMapping(value = "/getSelfAllPersonalInfo.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"USER"})
    public Result getSelfAllPersonalInfo() throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
        UserInfo userInfo = userService.findUserByUsername(username);
        return Result.build(Result.RESULTTYPE_SUCCESS, userInfo);
    }

    @RequestMapping(value = "/getSelfBasicPersonalInfo.do", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    @RolesAllowed({"USER"})
    public Result getSelfBasicPersonalInfo() throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
        UserInfo userInfo = userService.findBasicInfoOfUserByUsername(username);
        return Result.build(Result.RESULTTYPE_SUCCESS, userInfo);
    }

    //设置个人信息，不能修改密码和邮箱
    @RequestMapping(value = "/setSelfUserInfo.do", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    @RolesAllowed({"USER"})
    public Result setSelfUserInfo(@RequestParam("userInfo") UserInfo userInfo) throws Exception {
        if (userInfo.getPassword() != null || userInfo.getEmail() != null) {
            throw new Exception();
        }
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
        UserInfo userFound = userService.findBasicInfoOfUserByUsername(username);
        userService.updateUserInfo(userFound.getId(), userInfo);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @GetMapping(value = "/getSelfMessages.do")
    @RolesAllowed({"USER"})
    public Result getSelfMessages() throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
        List<Message> messages = messageService.findAllByUsername(username);
        return Result.build(Result.RESULTTYPE_SUCCESS, messages);
    }

    @PostMapping(value = "/deleteMessageById.do")
    @RolesAllowed({"USER"})
    public Result deleteMessageById(@RequestParam("messageId") Integer messageId) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
        messageService.deleteById(messageId, username);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }


}
