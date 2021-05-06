package cn.sqh.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.List;

@Data
@javax.persistence.Table(name = "tables")
public class Table {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private List<TableField> tableFieldList;
    private String tableName;
    private List<SubmitTable> submitTableList;

    private Group group;


}
