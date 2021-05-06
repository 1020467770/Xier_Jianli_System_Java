package cn.sqh.dao;

import cn.sqh.domain.Group;
import cn.sqh.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IPermissionDao extends Mapper<Permission> {

    @Select("select * from permission where id in(select permissionId from role__permission where roleId=#{id})")
    public Permission findPermissionByRoleId(Integer id) throws Exception;

    @Select("select * from permission")
    List<Permission> findAll()throws Exception;

    @Insert("insert into permission(permissionDesc,url) values(#{permissionDesc},#{url})")
    void save(Permission permission) throws Exception;
}
