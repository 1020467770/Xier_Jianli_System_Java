package cn.sqh.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "message")
public class Message {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Date sendTime;
    private String content;

}
