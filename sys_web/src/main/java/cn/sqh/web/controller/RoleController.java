package cn.sqh.web.controller;

import cn.sqh.domain.result.Result;
import cn.sqh.domain.Role;
import cn.sqh.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    //给角色添加权限的方法
    @RequestMapping(value = "/addPermissionsToRole.do", method = RequestMethod.POST)
    @RolesAllowed("SADMIN")
    public Result addPermissionsToRole(@RequestParam(name = "roleId", required = true) Integer roleId,
                                       @RequestParam(name = "permissionIds", required = true) Integer[] permissionIds) throws Exception {
        roleService.addPermissionToRole(roleId, permissionIds);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/addOnePermissionToRole.do", method = RequestMethod.POST)
    @RolesAllowed("SADMIN")
    public Result addOnePermissionToRole(@RequestParam(name = "roleId", required = true) Integer roleId,
                                         @RequestParam(name = "permissionId", required = true) Integer permissionId) throws Exception {
        roleService.addPermissionToRole(roleId, permissionId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/removePermissionsFromRole.do", method = RequestMethod.POST)
    @RolesAllowed("SADMIN")
    public Result removePermissionsFromRole(@RequestParam(name = "roleId", required = true) Integer roleId,
                                            @RequestParam(name = "permissionIds", required = true) Integer[] permissionIds) throws Exception {
        roleService.removePermissionFromRole(roleId, permissionIds);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @RequestMapping(value = "/removeOnePermissionFromRole.do", method = RequestMethod.POST)
    @RolesAllowed("SADMIN")
    public Result removeOnePermissionFromRole(@RequestParam(name = "roleId", required = true) Integer roleId,
                                              @RequestParam(name = "permissionId", required = true) Integer permissionId) throws Exception {
        roleService.removePermissionFromRole(roleId, permissionId);
        return Result.build(Result.RESULTTYPE_SUCCESS, null);
    }

    @GetMapping("/findAll.do")
    @RolesAllowed("SADMIN")
    public Result findAll() throws Exception {
        List<Role> roleList = roleService.findAll();
        return Result.build(Result.RESULTTYPE_SUCCESS, roleList);
    }


}
