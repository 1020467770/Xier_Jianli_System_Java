package cn.sqh.service.impl;

import cn.sqh.dao.ISubmitTableDao;
import cn.sqh.dao.ITableDao;
import cn.sqh.domain.SubmitTable;
import cn.sqh.domain.Table;
import cn.sqh.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TableServiceImpl implements ITableService {

    @Autowired
    private ITableDao tableDao;

    @Autowired
    private ISubmitTableDao submitTableDao;

    @Override
    public void addTableFieldToTable(Integer tableId, Integer[] fieldIds) throws Exception {
        for (Integer fieldId : fieldIds) {
            tableDao.addTableFieldToTable(tableId, fieldId);
        }
    }

    @Override
    public void addTableFieldToTable(Integer tableId, Integer fieldId) throws Exception {
        tableDao.addTableFieldToTable(tableId, fieldId);
    }

    @Override
    public void removeTableFieldFromTable(Integer tableId, Integer[] fieldIds) throws Exception {
        for (Integer fieldId : fieldIds) {
            tableDao.removeTableFieldFromTable(tableId, fieldId);
        }
    }

    @Override
    public void removeTableFieldFromTable(Integer tableId, Integer fieldId) throws Exception {
        tableDao.removeTableFieldFromTable(tableId, fieldId);
    }

    @Override
    public void saveNewSubmitTable(Integer tableId, SubmitTable submitTable) throws Exception {
        submitTableDao.saveNewSubmitTable(tableId, submitTable);
    }

    @Override
    public List<SubmitTable> findAllSubmitsByGroupId(Integer tableId) throws Exception {
        Table table = tableDao.findTableById(tableId);
        return table.getSubmitTableList();
    }

    @Override
    public Table finFormworkByTableId(Integer tableId) throws Exception {
        return tableDao.findFromworkById(tableId);
    }


}
