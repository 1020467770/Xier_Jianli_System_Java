package cn.sqh.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "syslog")
public class SysLog {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private Date visitTime;
    private String username;
    private String ip;
    private String url;
    private Long executionTime;
    private String method;
    private String authority;

}
