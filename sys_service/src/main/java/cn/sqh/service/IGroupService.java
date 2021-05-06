package cn.sqh.service;

import cn.sqh.domain.Group;

import java.util.List;

public interface IGroupService {
    void setNewGroupDesc(Integer groupId, String groupDesc) throws Exception;

    void save(Group group) throws Exception;

    List<Group> findAll(boolean isAno) throws Exception;

    Group findGroupById(Integer id) throws Exception;

    List<Group> findGroupsByUsername(String username) throws Exception;

    void deleteGroupById(Integer groupId) throws Exception;
}
