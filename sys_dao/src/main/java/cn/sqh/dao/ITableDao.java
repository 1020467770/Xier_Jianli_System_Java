package cn.sqh.dao;


import cn.sqh.domain.Group;
import cn.sqh.domain.SubmitTable;
import cn.sqh.domain.Table;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ITableDao extends Mapper<Table> {

    @Select("select * from tables where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "tableName", column = "tableName"),
            @Result(property = "tableFieldList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.ITableFieldDao.findFieldByTableId")
            ),
            @Result(property = "submitTableList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.ISubmitTableDao.findSubmitTableByTableId")
            )
    })
    public Table findTableById(Integer id) throws Exception;

    @Select("select * from tables where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "tableName", column = "tableName")
    })
    public Table findBasicTableInfoById(Integer id) throws Exception;

    @Select("select * from tables where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "tableName", column = "tableName"),
            @Result(property = "tableFieldList", column = "id", javaType = List.class,
                    many = @Many(select = "cn.sqh.dao.ITableFieldDao.findFieldByTableId")
            )
    })
    public Table findFromworkById(Integer id) throws Exception;

    @Insert("insert into tables__table_field(tableId,tableFieldId) values(#{tableId},#{tableFieldId})")
    void addTableFieldToTable(@Param("tableId") Integer tableId, @Param("tableFieldId") Integer fieldId) throws Exception;

    @Delete("delete from tables__table_field where tableId=#{tableId} and tableFieldId=#{tableFieldId}")
    void removeTableFieldFromTable(@Param("tableId") Integer tableId, @Param("tableFieldId") Integer fieldId) throws Exception;

    @Insert("insert into submit_table(fileName,tableId,checkedStatus,updateDate,content) values(#{table.fileName},#{tableId},#{table.checkedStatus},#{table.updateDate},#{table.content})")
    void saveNewSubmitTable(@Param("tableId") Integer tableId, @Param("table") SubmitTable submitTable) throws Exception;

    @Insert("insert into tables(groupId,tableName) values(#{group.id},#{tableName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer addNewTable(Table table) throws Exception;

    @Select("select id from groups where tableId=#{tableId}")
    Integer findGroupByTableId(Integer tableId) throws Exception;
}
