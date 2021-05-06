package cn.sqh.service;


import cn.sqh.domain.SubmitTable;
import cn.sqh.domain.Table;

import java.util.List;

public interface ITableService {
    void addTableFieldToTable(Integer tableId, Integer[] fieldIds) throws Exception;

    void addTableFieldToTable(Integer tableId, Integer fieldId) throws Exception;

    void removeTableFieldFromTable(Integer tableId, Integer[] fieldIds) throws Exception;

    void removeTableFieldFromTable(Integer tableId, Integer fieldId) throws Exception;

    void saveNewSubmitTable(Integer tableId, SubmitTable submitTable) throws Exception;

    List<SubmitTable> findAllSubmitsByGroupId(Integer tableId) throws Exception;

    Table finFormworkByTableId(Integer tableId) throws Exception;
}
