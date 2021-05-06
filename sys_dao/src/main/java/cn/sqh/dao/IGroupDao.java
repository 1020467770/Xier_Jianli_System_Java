package cn.sqh.dao;


import cn.sqh.domain.Group;
import cn.sqh.domain.SysLog;
import cn.sqh.domain.Table;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IGroupDao extends Mapper<Group> {

    @Select("select * from groups where id in(select groupId from users__groups where userId=#{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "groupName", column = "groupName"),
            @Result(property = "groupDesc", column = "groupDesc"),
            @Result(property = "table", column = "tableId", javaType = Table.class,
                    one = @One(select = "cn.sqh.dao.ITableDao.findTableById")
            )
    })
    public Group findGroupByUserId(Integer userId) throws Exception;

    @Select("select * from groups where id in(select groupId from users__groups where userId=#{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "groupName", column = "groupName"),
            @Result(property = "groupDesc", column = "groupDesc"),
            @Result(property = "table", column = "tableId", javaType = Table.class,
                    one = @One(select = "cn.sqh.dao.ITableDao.findTableById")
            )
    })
    public List<Group> findGroupsByUserId(Integer userId) throws Exception;

    @Select("select * from groups where id in(select groupId from users__groups where userId=#{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "groupName", column = "groupName"),
            @Result(property = "groupDesc", column = "groupDesc"),
            @Result(property = "table", column = "tableId", javaType = Table.class,
                    one = @One(select = "cn.sqh.dao.ITableDao.findBasicTableInfoById")
            )
    })
    public List<Group> findBasicGroupInfoByUserId(Integer userId) throws Exception;

    @Update("update groups set groupDesc=#{groupDesc} where id =#{groupId}")
    void updateGroupDesc(@Param("groupId") Integer groupId, @Param("groupDesc") String groupDesc) throws Exception;

    @Insert("insert into groups(groupName,tableId,groupDesc) values(#{groupName},#{table.id},#{groupDesc})")
    void save(Group group) throws Exception;

    @Select("select * from groups")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "groupName", column = "groupName"),
            @Result(property = "groupDesc", column = "groupDesc"),
            @Result(property = "table", column = "tableId", javaType = Table.class,
                    one = @One(select = "cn.sqh.dao.ITableDao.findTableById")
            ),
            @Result(property = "users", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IUserDao.findBasicInfoOfUserByGroupId")
            )
    })
    List<Group> findAll() throws Exception;

    @Select("select * from groups where id =#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "groupName", column = "groupName"),
            @Result(property = "groupDesc", column = "groupDesc"),
            @Result(property = "table", column = "tableId", javaType = Table.class,
                    one = @One(select = "cn.sqh.dao.ITableDao.findBasicTableInfoById")
            ),
            @Result(property = "users", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IUserDao.findBasicInfoOfUserByGroupId")
            )
    })
    Group findGroupById(Integer id) throws Exception;
}
