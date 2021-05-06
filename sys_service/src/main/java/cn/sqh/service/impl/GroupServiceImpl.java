package cn.sqh.service.impl;

import cn.sqh.dao.IGroupDao;
import cn.sqh.dao.ITableDao;
import cn.sqh.dao.IUserDao;
import cn.sqh.domain.Group;
import cn.sqh.domain.Table;
import cn.sqh.domain.UserInfo;
import cn.sqh.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements IGroupService {

    @Autowired
    private IGroupDao groupDao;

    @Autowired
    private ITableDao tableDao;

    @Autowired
    private IUserDao userDao;

    @Override
    public void setNewGroupDesc(Integer groupId, String groupDesc) throws Exception {
        groupDao.updateGroupDesc(groupId, groupDesc);
    }

    @Override
    public void save(Group group) throws Exception {
        Table table = new Table();
        table.setGroup(group);
        table.setTableName(group.getGroupName() + "组的简历表格");
        tableDao.addNewTable(table);
        group.setTable(table);
        groupDao.save(group);
    }

    @Override
    public List<Group> findAll(boolean isAno) throws Exception {
        if (isAno) {
            return groupDao.selectAll();
        } else {
            return groupDao.findAll();
        }
    }

    @Override
    public Group findGroupById(Integer id) throws Exception {
        return groupDao.findGroupById(id);
    }

    @Override
    public List<Group> findGroupsByUsername(String username) throws Exception {
        final UserInfo user = userDao.findBasicInfoOfUserByUsername(username);
        if (user == null) {
            throw new Exception();
        }
//        return groupDao.findGroupsByUserId(user.getId());//获取详细信息
        return groupDao.findBasicGroupInfoByUserId(user.getId());//获取基本信息
    }

    @Override
    public void deleteGroupById(Integer groupId) throws Exception {
        groupDao.deleteByPrimaryKey(groupId);
    }
}
