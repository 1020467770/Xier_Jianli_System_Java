package cn.sqh.service;

import cn.sqh.domain.SysLog;

import java.util.List;

public interface ISysLogService {

    void save(SysLog sysLog) throws Exception;

    List<SysLog> findAll() throws Exception;

    List<SysLog> findByPage(Integer pageNum) throws Exception;
}
