package cn.sqh.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "users")
public class UserInfo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String major;
    private String email;
    private String QQ;
    private String phoneNum;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createDate;
    private Date updateDate;
    private Integer activeStatus;
    private String activeStatusStr;
    private String code;
    private List<Role> roleList;
    private List<Group> groupList;
    private List<Message> messageList;

}
