package cn.sqh.dao;


import cn.sqh.domain.Group;
import cn.sqh.domain.Role;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IRoleDao extends Mapper<Role> {

    @Select("select * from role where id in(select roleId from users__role where userId=#{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissionList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IPermissionDao.findPermissionByRoleId")
            )
    })
    public Role findRoleByUserId(Integer id) throws Exception;

    @Insert("insert into role__permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    public void addPermissionToRole(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId) throws Exception;

    @Delete("delete from role__permission where roleId=#{roleId} and permissionId=#{permissionId}")
    public void removePermissionFromRole(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId) throws Exception;

    @Select("select * from role")
    List<Role> findAll() throws Exception;
}
