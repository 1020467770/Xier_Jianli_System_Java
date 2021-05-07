package cn.sqh.web.controller;

import cn.sqh.domain.Group;
import cn.sqh.domain.UserInfo;
import cn.sqh.domain.result.Result;
import cn.sqh.service.IGroupService;
import cn.sqh.web.interfaces.LimitRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequestMapping("/group")
@RestController
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @RequestMapping(value = "/addNewGroup.do", method = RequestMethod.POST)
    @RolesAllowed("SADMIN")
    public Result addNewGroup(Group group) throws Exception {
        groupService.save(group);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/setNewGroupDesc.do", method = RequestMethod.POST)
    @RolesAllowed("GADMIN")
    public Result setNewGroupDesc(@RequestParam("groupId") Integer groupId,
                                  @RequestParam("groupDesc") String groupDesc) throws Exception {
        groupService.setNewGroupDesc(groupId, groupDesc);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @GetMapping("/findAll.do")
    @RolesAllowed("SADMIN")
    @LimitRequest(time = 1000 * 10, count = 10)
    public Result findAll() throws Exception {
        List<Group> groupList = groupService.findAll(false);
        return Result.build(Result.RESULTTYPE_SUCCESS, groupList);
    }

    @GetMapping("/findGroupByGroupId.do")
    @RolesAllowed("SADMIN")
    @LimitRequest(time = 1000 * 10, count = 10)
    public Result findGroupByGroupId(@RequestParam("groupId") Integer groupId) throws Exception {
        Group group = groupService.findGroupById(groupId);
        return Result.build(Result.RESULTTYPE_SUCCESS, group);
    }

    @PostMapping(value = "/deleteGroupById.do")
    @RolesAllowed({"SADMIN"})
    public Result deleteGroupById(@RequestParam(name = "groupId", required = true) Integer groupId) throws Exception {
        groupService.deleteGroupById(groupId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @GetMapping("/findSelfGroupInfo.do")
    @RolesAllowed("GADMIN")
    @LimitRequest(time = 1000 * 10, count = 10)
    public Result findSelfGroupInfo() throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();
        final List<Group> groups = groupService.findGroupsByUsername(username);
        return Result.build(Result.RESULTTYPE_SUCCESS, groups);
    }

    @GetMapping("/anonymous/findAll.do")
    @PermitAll
    @LimitRequest(time = 1000 * 10, count = 10)
    public Result findAll_Ano() throws Exception {
        List<Group> groupList = groupService.findAll(true);
        return Result.build(Result.RESULTTYPE_SUCCESS, groupList);
    }

}
