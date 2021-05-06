package cn.sqh.dao;


import cn.sqh.domain.Group;
import cn.sqh.domain.Message;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface IMessageDao extends Mapper<Message> {

    @Select("select * from users__message um,message m where um.userId=#{userId} and m.id=um.messageId ")
    Message findMessageByUserId(Integer userId) throws Exception;

}
