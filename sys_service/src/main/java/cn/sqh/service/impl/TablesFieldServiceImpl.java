package cn.sqh.service.impl;

import cn.sqh.dao.ITableFieldDao;
import cn.sqh.domain.TableField;
import cn.sqh.service.ITablesFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TablesFieldServiceImpl implements ITablesFieldService {

    @Autowired
    private ITableFieldDao tableFieldDao;

    @Override
    public List<TableField> findAll() throws Exception {
        return tableFieldDao.findAll();
    }
}
