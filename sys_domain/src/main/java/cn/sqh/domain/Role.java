package cn.sqh.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@Table(name = "role")
public class Role {

    public static final Integer ROLE_SADMIN = 1;
    public static final Integer ROLE_GADMIN = 2;
    public static final Integer ROLE_USER = 3;
    public static final Integer ROLE_ANONYMOUS = 4;

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String roleName;
    private String roleDesc;
    private List<Permission> permissionList;
    private List<UserInfo> users;


}
