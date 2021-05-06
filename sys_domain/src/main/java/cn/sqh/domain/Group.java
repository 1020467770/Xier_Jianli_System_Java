package cn.sqh.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;


@Data
@javax.persistence.Table(name = "groups")
public class Group {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Column(name = "groupName")
    private String groupName;
    @Column(name = "groupDesc")
    private String groupDesc;

    private List<UserInfo> users;
    private Table table;

}
