package cn.sqh.dao;


import cn.sqh.domain.Group;
import cn.sqh.domain.Message;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IMessageDao extends Mapper<Message> {

    @Select("select * from users__message um,message m where um.userId=#{userId} and m.id=um.messageId ")
    List<Message> findMessageByUserId(Integer userId) throws Exception;

    @Select("select * from message where userId=#{userId} ")
    List<Message> selectMessagesByUserId(Integer userId) throws Exception;

    @Delete("delete from message where id=#{messageId} and userId=#{userId}")
    void deleteMessageFromMessageContainer(@Param("messageId") Integer messageId, @Param("userId") Integer userId);

    @Insert("insert into message(content,userId) values(#{content},#{userId})")
    void save(@Param("content") String content, @Param("userId") Integer userId);

}
