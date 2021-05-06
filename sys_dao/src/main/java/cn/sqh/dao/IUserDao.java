package cn.sqh.dao;


import cn.sqh.domain.Group;
import cn.sqh.domain.Role;
import cn.sqh.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IUserDao extends Mapper<UserInfo> {

    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "major", column = "major"),
            @Result(property = "email", column = "email"),
            @Result(property = "QQ", column = "QQ"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "createDate", column = "createDate"),
            @Result(property = "updateDate", column = "updateDate"),
            @Result(property = "activeStatus", column = "activeStatus"),
            @Result(property = "code", column = "code"),
            @Result(property = "roleList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IRoleDao.findRoleByUserId")
            ),
            @Result(property = "groupList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IGroupDao.findGroupByUserId")
            ),
            @Result(property = "messageList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IMessageDao.findMessageByUserId")
            )
    })
    public UserInfo findByUsername(String username) throws Exception;

    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "major", column = "major"),
            @Result(property = "email", column = "email"),
            @Result(property = "QQ", column = "QQ"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "createDate", column = "createDate"),
            @Result(property = "updateDate", column = "updateDate"),
            @Result(property = "activeStatus", column = "activeStatus"),
            @Result(property = "code", column = "code"),
            @Result(property = "roleList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IRoleDao.findRoleByUserId")
            ),
            @Result(property = "groupList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IGroupDao.findGroupByUserId")
            ),
            @Result(property = "messageList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.IMessageDao.findMessageByUserId")
            )
    })
    public UserInfo findById(Integer id) throws Exception;

    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "major", column = "major"),
            @Result(property = "email", column = "email"),
            @Result(property = "QQ", column = "QQ"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "createDate", column = "createDate"),
            @Result(property = "updateDate", column = "updateDate"),
            @Result(property = "activeStatus", column = "activeStatus"),
            @Result(property = "code", column = "code")
    })
    public UserInfo findBasicInfoOfUserById(Integer id) throws Exception;

    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "major", column = "major"),
            @Result(property = "email", column = "email"),
            @Result(property = "QQ", column = "QQ"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "createDate", column = "createDate"),
            @Result(property = "updateDate", column = "updateDate"),
            @Result(property = "activeStatus", column = "activeStatus"),
            @Result(property = "code", column = "code")
    })
    UserInfo findBasicInfoOfUserByUsername(String username) throws Exception;

    @Select("select * from users where id in(select userId from users__groups where groupId=#{id})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "major", column = "major"),
            @Result(property = "email", column = "email"),
            @Result(property = "QQ", column = "QQ"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "activeStatus", column = "activeStatus"),
    })
    UserInfo findBasicInfoOfUserByGroupId(Integer groupId) throws Exception;

    @Select("select * from role where id not in (select roleId from users_role where userId =#{userId})")
    public List<Role> findOtherRoles(Integer userId) throws Exception;

    @Insert("insert into users__role(userId,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") Integer userId, @Param("roleId") Integer roleId) throws Exception;

    @Insert("insert into users__groups(userId,username,groupId) values(#{userId},#{username},#{groupId})")
    void addGroupToUser(@Param("userId") Integer userId, @Param("username") String username, @Param("groupId") Integer groupId) throws Exception;

    @Delete("delete from users__role where userId=#{userId} and roleId=#{roleId}")
    void removeRoleFromUser(@Param("userId") Integer userId, @Param("roleId") Integer roleId) throws Exception;

    @Insert("insert into users(username,password,name,major,email,QQ,phoneNum,activeStatus,code,updateDate) " +
            "values(#{username},#{password},#{name},#{major},#{email},#{QQ},#{phoneNum},#{activeStatus},#{code},#{updateDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer save(UserInfo userInfo) throws Exception;

    @Update({
            "<script>",
            "update users set ",
            "<if test = \"name != null\"> ",
            "name=#{name}, ",
            "</if> ",
            "<if test = \"QQ != null\"> ",
            "QQ=#{QQ}, ",
            "</if> ",
            "<if test = \"phoneNum != null\"> ",
            "phoneNum=#{phoneNum}, ",
            "</if> ",
            "updateDate=CURRENT_TIMESTAMP ",
            "WHERE id=#{userId}",
            "</script>"
    })
    void updateUserInfo(@Param("userId") Integer userId,
                        @Param("name") String name,
                        @Param("QQ") String qq,
                        @Param("phoneNum") String phoneNum) throws Exception;

    @Select("select * from users")
    List<UserInfo> findAllUsersBasicInfo() throws Exception;

    @Select("select * from groups where id in(select groupId from users__groups where userId=#{userId})")
    public List<Group> findAllGroupsByUserId(Integer userId) throws Exception;

}
