package cn.sqh.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "submit_table")
public class SubmitTable {

    static final public int CHECKEDTYPE_NOTSTART = 0;
    static final public int CHECKEDTYPE_CHECKING = 1;
    static final public int CHECKEDTYPE_CHECKED_SUCCESS = 2;
    static final public int CHECKEDTYPE_CHECKED_FAILED = 3;


    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String fileName;
    private Date sendTime;
    private Integer checkedStatus;
    private Date updateDate;

    private UserInfo checker;

    private String content;


}
