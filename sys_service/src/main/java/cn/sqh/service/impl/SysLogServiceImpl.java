package cn.sqh.service.impl;

import cn.sqh.dao.ISysLogDao;
import cn.sqh.domain.SysLog;
import cn.sqh.service.ISysLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private ISysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) throws Exception {
        sysLogDao.save(sysLog);
    }

    @Override
    public List<SysLog> findAll() throws Exception {
        return sysLogDao.findAll();
    }

    @Override
    public List<SysLog> findByPage(Integer pageNum) throws Exception {
        PageHelper.startPage(pageNum, 15);
        return sysLogDao.findAll();
    }
}
