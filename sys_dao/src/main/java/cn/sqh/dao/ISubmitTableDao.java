package cn.sqh.dao;


import cn.sqh.domain.Group;
import cn.sqh.domain.SubmitTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface ISubmitTableDao extends Mapper<SubmitTable> {

    @Select("select * from submit_table where tableId=#{tableId}")
    SubmitTable findSubmitTableByTableId(Integer tableId) throws Exception;

    @Update("update submit_table set checkerId=#{checkerId},checkedStatus=#{checkResultStatus},updateDate=CURRENT_TIMESTAMP where id =#{submitTableId}")
    void checkSubmitTable(@Param("submitTableId") Integer submitTableId,
                          @Param("checkerId") Integer checkerId,
                          @Param("checkResultStatus") Integer checkResultStatus) throws Exception;

    @Update("update submit_table set checkedStatus=1 where id =#{submitTableId} ")
    void startCheckSubmitTable(Integer submitTableId) throws Exception;

    @Select("select * from submit_table where id=#{submitTableId}")
    SubmitTable findSubmitTableBySubmitTableId(Integer submitTableId) throws Exception;

    @Insert("insert into submit_table(fileName,tableId,checkedStatus,updateDate,content) values(#{table.fileName},#{tableId},#{table.checkedStatus},#{table.updateDate},#{table.content})")
    void saveNewSubmitTable(@Param("tableId") Integer tableId, @Param("table") SubmitTable submitTable) throws Exception;

}
