package cn.sqh.service;

import cn.sqh.domain.Role;

import java.util.List;

public interface IRoleService {

    void addPermissionToRole(Integer roleId, Integer[] permissionIds) throws Exception;

    void addPermissionToRole(Integer roleId, Integer permissionIds) throws Exception;

    void removePermissionFromRole(Integer roleId, Integer permissionId) throws Exception;

    void removePermissionFromRole(Integer roleId, Integer[] permissionIds) throws Exception;

    List<Role> findAll() throws Exception;
}
