package cn.sqh.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "permission")
public class Permission {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String permissionDesc;
    private String url;
    private List<Role> roleList;

}
