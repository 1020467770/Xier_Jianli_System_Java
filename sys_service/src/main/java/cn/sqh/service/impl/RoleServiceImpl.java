package cn.sqh.service.impl;

import cn.sqh.dao.IRoleDao;
import cn.sqh.domain.Role;
import cn.sqh.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public void addPermissionToRole(Integer roleId, Integer[] permissionIds) throws Exception {
        for (Integer permissionId : permissionIds) {
            roleDao.addPermissionToRole(roleId, permissionId);
        }
    }

    @Override
    public void addPermissionToRole(Integer roleId, Integer permissionId) throws Exception {
        roleDao.addPermissionToRole(roleId, permissionId);
    }

    @Override
    public void removePermissionFromRole(Integer roleId, Integer permissionId) throws Exception {
        roleDao.removePermissionFromRole(roleId, permissionId);
    }

    @Override
    public void removePermissionFromRole(Integer roleId, Integer[] permissionIds) throws Exception {
        for (Integer permissionId : permissionIds) {
            roleDao.removePermissionFromRole(roleId, permissionId);
        }
    }

    @Override
    public List<Role> findAll() throws Exception {
        return roleDao.findAll();
    }
}
