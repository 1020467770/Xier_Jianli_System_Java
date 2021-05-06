package cn.sqh.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tables_field")
public class TableField {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String fieldName;

}
