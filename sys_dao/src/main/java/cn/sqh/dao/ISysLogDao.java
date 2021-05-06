package cn.sqh.dao;

import cn.sqh.domain.Group;
import cn.sqh.domain.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ISysLogDao extends Mapper<SysLog> {

    @Insert("insert into syslog(visitTime,username,IP,url,executionTime,method,authority) values(#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method},#{authority})")
    void save(SysLog sysLog) throws Exception;

    @Select("select * from syslog")
    List<SysLog> findAll() throws Exception;
}
