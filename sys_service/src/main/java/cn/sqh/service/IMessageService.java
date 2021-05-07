package cn.sqh.service;

import cn.sqh.domain.Message;

import java.util.List;

public interface IMessageService {
    List<Message> findAllByUsername(String username) throws Exception;

    void deleteById(Integer id, String username) throws Exception;

    void createNewMessageToUser(String message, Integer userId) throws Exception;

    void notifyNewMessageToGroup(String message, Integer groupId, boolean isForAdmin) throws Exception;

    void notifyNewMessageToGroupByTableId(String message, Integer tableId, boolean isForAdmin) throws Exception;
}
