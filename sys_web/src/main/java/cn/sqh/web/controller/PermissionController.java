package cn.sqh.web.controller;

import cn.sqh.domain.Permission;
import cn.sqh.domain.result.Result;
import cn.sqh.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequestMapping("/permission")
@RestController
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @RolesAllowed("SADMIN")
    public Result save(Permission permission) throws Exception {
        permissionService.save(permission);
        return Result.build(Result.RESULTTYPE_SUCCESS,null);
    }

    @GetMapping("/findAll.do")
    @RolesAllowed("SADMIN")
    public Result findAll() throws Exception {
        List<Permission> permissionList = permissionService.findAll();
        return Result.build(Result.RESULTTYPE_SUCCESS, permissionList);
    }
}
