package cn.sqh.service;

import cn.sqh.domain.TableField;

import java.util.List;

public interface ITablesFieldService {
    List<TableField> findAll() throws Exception;
}
