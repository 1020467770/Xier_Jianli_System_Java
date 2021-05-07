package cn.sqh.service.impl;

import cn.sqh.dao.*;
import cn.sqh.domain.Group;
import cn.sqh.domain.Message;
import cn.sqh.domain.Role;
import cn.sqh.domain.UserInfo;
import cn.sqh.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IMessageDao messageDao;

    @Autowired
    private IGroupDao groupDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private ITableDao tableDao;

    @Override
    public List<Message> findAllByUsername(String username) throws Exception {
        final UserInfo user = userDao.findBasicInfoOfUserByUsername(username);
        if (user == null) {
            throw new Exception("未找到该用户信息");
        }
        return messageDao.selectMessagesByUserId(user.getId());
    }

    @Override
    public void deleteById(Integer messageId, String username) throws Exception {
        final UserInfo user = userDao.findBasicInfoOfUserByUsername(username);
        if (user == null) {
            throw new Exception("未找到该用户信息");
        }
        messageDao.deleteMessageFromMessageContainer(user.getId(), messageId);
    }

    @Override
    public void createNewMessageToUser(String message, Integer userId) throws Exception {
        final UserInfo user = userDao.findBasicInfoOfUserById(userId);
        if (user == null) {
            throw new Exception("未找到该用户信息");
        }
        messageDao.save(message, userId);
    }

    @Override
    public void notifyNewMessageToGroup(String message, Integer groupId, boolean isForAdmin) throws Exception {
        System.out.println("****************进来了2");
        final Group group = groupDao.findGroupById(groupId);
        if (group == null) {
            throw new Exception("没有该小组信息");
        }
        final List<UserInfo> users = group.getUsers();
        users.forEach((user) -> {
            try {
                final Integer userId = user.getId();
                final List<Integer> roleIds = roleDao.findRoleIdsByUserId(userId);
                if (isForAdmin) {
                    if (roleIds.contains(Role.ROLE_GADMIN)) {//小组管理员专属消息
                        createNewMessageToUser(message, userId);
                    }
                } else {
                    createNewMessageToUser(message, userId);//普通用户也可收到的系统消息
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void notifyNewMessageToGroupByTableId(String message, Integer tableId, boolean isForAdmin) throws Exception {
        System.out.println("*********进来了A");
        final Integer groupId = tableDao.findGroupByTableId(tableId);
        notifyNewMessageToGroup(message, groupId, isForAdmin);
    }
}
