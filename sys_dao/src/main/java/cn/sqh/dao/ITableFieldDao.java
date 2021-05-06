package cn.sqh.dao;


import cn.sqh.domain.Group;
import cn.sqh.domain.TableField;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ITableFieldDao extends Mapper<TableField> {

    @Select("select * from tables_field where id in(select tableFieldId from tables__table_field where tableId=#{tableId})")
    @Results({
            @Result(id = true, property = "id",column = "id"),
            @Result(property = "fieldName",column = "fieldName"),
    })
    public TableField findFieldByTableId(Integer tableId) throws Exception;

    @Select("select * from tables_field")
    List<TableField> findAll()throws Exception;
}
